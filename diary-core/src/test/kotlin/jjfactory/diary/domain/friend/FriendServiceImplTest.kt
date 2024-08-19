package jjfactory.diary.domain.friend

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jjfactory.diary.common.exception.AccessForbiddenException
import jjfactory.diary.common.exception.DuplicateRequestException
import jjfactory.diary.infrastructure.friend.FriendRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class FriendServiceImplTest {
    @Autowired
    lateinit var friendService: FriendServiceImpl
    @PersistenceContext
    lateinit var entityManager: EntityManager
    @Autowired
    lateinit var friendRepository: FriendRepository

    @Transactional
    @Test
    fun `셀프 친구요청시 익셉션`() {
        assertThatThrownBy {
            friendService.sendRequest(
                senderId = 2L,
                receiverId = 2L
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Transactional
    @Test
    fun `동일한 친구 요청 두번시 익셉션`() {
        friendService.sendRequest(
            senderId = 2L,
            receiverId = 3L
        )

        assertThatThrownBy {
            friendService.sendRequest(
                senderId = 2L,
                receiverId = 3L
            )
        }.isInstanceOf(DuplicateRequestException::class.java)
    }

    @Transactional
    @Test
    fun `친구 요청 성공`() {
        val result = friendService.sendRequest(
            senderId = 2L,
            receiverId = 3L
        )

        assertThat(result).isNotNull()

    }

    @Transactional
    @Test
    fun `수신자 아니면 친구 수락 실패`() {
        val id = friendService.sendRequest(
            senderId = 2L,
            receiverId = 3L
        )

        assertThatThrownBy {
            friendService.accept(
                id = id,
                userId = 4L
            )
        }.isInstanceOf(AccessForbiddenException::class.java)
    }

    @Transactional
    @Test
    fun `빠르게 요청 오면 리미트 넘음`(){
        val executorService = Executors.newFixedThreadPool(30)
        val latch = CountDownLatch(30)

        for (i in 1..30) {
            executorService.execute {
                try {
                    friendService.sendRequest(
                        senderId = i.toLong(),
                        receiverId = 20
                    )
                } catch (e: Exception) {
                    println("Exception: ${e.message}")
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
        executorService.shutdown()

        assertThat(friendRepository.countByReceiverId(20)).isGreaterThan(20)
    }

}