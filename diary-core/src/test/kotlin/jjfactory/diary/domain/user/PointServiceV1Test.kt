package jjfactory.diary.domain.user

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jjfactory.diary.TestEntityFactory
import jjfactory.diary.infrastructure.user.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class PointServiceV1Test {
    @Autowired
    private lateinit var pointService: PointServiceV1
    @Autowired
    private lateinit var userRepository: UserRepository
    private val testEntityFactory = TestEntityFactory()

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Transactional
    @Test
    fun `보유 포인트보다 많이보내면 익셉션`() {
        val user1 = testEntityFactory.ofUser()
        val user2 = testEntityFactory.ofUser()
        entityManager.persist(user1)
        entityManager.persist(user2)


        val ownPoint = user1.point

        assertThatThrownBy {
            pointService.send(
                sourceUserId = user1.id!!,
                targetUserId = user2.id!!,
                point = ownPoint + 10
            )
        }.isInstanceOf(NotEnoughPointException::class.java)
    }

    @Transactional
    @Test
    fun `포인트 거래 성공`() {
        val user1 = testEntityFactory.ofUser()
        user1.pointUp(500)

        val user2 = testEntityFactory.ofUser()
        entityManager.persist(user1)
        entityManager.persist(user2)


        pointService.send(
            sourceUserId = user1.id!!,
            targetUserId = user2.id!!,
            point = user1.point
        )

        assertThat(user1.point).isEqualTo(0)
        assertThat(user2.point).isEqualTo(500)
    }

    @Transactional
    @Test
    fun `동시성 이슈가 발생가능`() {
        val user1 = testEntityFactory.ofUser()
        user1.pointUp(1000)
        val user2 = testEntityFactory.ofUser()

        userRepository.save(user1)
        userRepository.save(user2)

        val executorService = Executors.newFixedThreadPool(2)
        val latch = CountDownLatch(2)

        for (i in 1..2) {
            executorService.execute {
                try {
                    pointService.send(
                        sourceUserId = user1.id!!,
                        targetUserId = user2.id!!,
                        point = 500
                    )
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()

        assertThat(user1.point).isEqualTo(0)
        assertThat(user2.point).isEqualTo(1000)
    }

}