package jjfactory.diary.domain.notification

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface NotificationService {
    fun readAllNotifications(userId: Long)
    fun storeCommentWriteNotification(sourceUserId: Long, targetUserId: Long)
    fun storeFriendRequestNotification(sourceUserId: Long, targetUserId: Long)
    fun getAllNotificationsByTargetId(
        targetUserId: Long,
        pageable: Pageable,
        isRead: Boolean?
    ): Page<NotificationInfo.List>
}