package jjfactory.diary.infrastructure.notification

import jjfactory.diary.domain.notification.Notification
import jjfactory.diary.domain.notification.NotificationReader
import jjfactory.diary.common.exception.ResourceNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class NotificationReaderImpl(
    private val notificationRepository: NotificationRepository
) : NotificationReader {
    override fun get(id: Long): Notification? {
        return notificationRepository.findByIdOrNull(id)
    }

    override fun getOrThrow(id: Long): Notification {
        return notificationRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("notification not found")
    }

    override fun findAllByTargetUserIdAndReadIsFalse(userId: Long): List<Notification> {
        return notificationRepository.findAllByTargetUserIdAndIsReadIsFalse(userId)
    }

    override fun getPageByTargetUserId(userId: Long): Page<Notification> {
        TODO("Not yet implemented")
    }
}