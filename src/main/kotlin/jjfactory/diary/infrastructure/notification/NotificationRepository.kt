package jjfactory.diary.infrastructure.notification

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import jjfactory.diary.domain.notification.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository: JpaRepository<Notification, Long>, KotlinJdslJpqlExecutor {
    fun findAllByTargetUserIdAndIsReadIsFalse(userId: Long): List<Notification>
}