package jjfactory.diary.domain.notification

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class NotificationServiceImpl(
    private val notificationReader: NotificationReader
) : NotificationService {
    override fun readAllNotifications(userId: Long) {
        notificationReader.findAllByTargetUserIdAndReadIsFalse(userId).forEach {
            it.read()
        }
    }
}