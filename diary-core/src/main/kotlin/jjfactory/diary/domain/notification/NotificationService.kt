package jjfactory.diary.domain.notification

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface NotificationService {
    fun readAllNotifications(userId: Long)
    fun getAllNotificationsByTargetId(
        targetUserId: Long,
        pageable: Pageable,
        isRead: Boolean?
    ): Page<NotificationInfo.List>

    fun store(sourceUserId: Long, targetUserId: Long, type: NotificationType)
}