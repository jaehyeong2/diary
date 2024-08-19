package jjfactory.diary.application

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jjfactory.diary.TestEntityFactory
import jjfactory.diary.domain.diary.comment.CommentCommand
import jjfactory.diary.infrastructure.notification.NotificationRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class CommentFacadeTest(){
    @Autowired
    lateinit var commentFacade: CommentFacade

    @Autowired
    lateinit var notificationRepository: NotificationRepository

    @PersistenceContext
    lateinit var entityManager: EntityManager

    private val testEntityFactory = TestEntityFactory()

    @Transactional
    @Test
    fun `작성자는 프라이빗 일기에도 코멘트 쓸 수 있다`(){
        val user = testEntityFactory.ofUser()
        entityManager.persist(user)

        val diary = testEntityFactory.ofPrivateDiary(user.id)
        entityManager.persist(diary)

        val command = CommentCommand.Create(
            diaryId = diary.id!!,
            content = "잘봣습니다~"
        )

        val result = commentFacade.writeComment(
            userId = user.id!!,
            command = command
        )

        assertThat(result).isNotNull
        assertThat(notificationRepository.findAll().size).isEqualTo(1)
    }
}