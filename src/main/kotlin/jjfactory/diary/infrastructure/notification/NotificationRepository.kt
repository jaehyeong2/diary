package jjfactory.diary.infrastructure.notification

import jjfactory.diary.domain.notification.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository: JpaRepository<Notification, Long> {
}