package jjfactory.diary.domain.user

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jjfactory.diary.TestEntityFactory
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class PointServiceImplTest {
    @Autowired
    private lateinit var pointService: PointServiceImpl
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

}