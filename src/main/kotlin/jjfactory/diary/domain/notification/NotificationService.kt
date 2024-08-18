package jjfactory.diary.domain.notification

interface NotificationService {
    fun readAllNotifications(userId: Long)
    fun storeCommentNotification(sourceUserId: Long, targetUserId: Long)
}