package jjfactory.diary.domain.notification

import jjfactory.diary.infrastructure.notification.NotificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class NotificationServiceImpl(
    private val notificationReader: NotificationReader,
    private val notificationRepository: NotificationRepository
) : NotificationService {
    override fun readAllNotifications(userId: Long) {
        notificationReader.findAllByTargetUserIdAndReadIsFalse(userId).forEach {
            it.read()
        }
    }

    override fun storeCommentNotification(sourceUserId: Long, targetUserId: Long){
        val initNotification = Notification(
            sourceUserId = sourceUserId,
            targetUserId = targetUserId,
            type = NotificationType.WRITE_COMMENT
        )

        notificationRepository.save(initNotification)
    }
}