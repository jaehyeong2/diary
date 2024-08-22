package jjfactory.diary.application

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jjfactory.diary.TestEntityFactory
import jjfactory.diary.infrastructure.notification.NotificationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CommentFacadeTest() {
    @Autowired
    lateinit var commentFacade: CommentFacade

    @Autowired
    lateinit var notificationRepository: NotificationRepository

    @PersistenceContext
    lateinit var entityManager: EntityManager

    private val testEntityFactory = TestEntityFactory()


}