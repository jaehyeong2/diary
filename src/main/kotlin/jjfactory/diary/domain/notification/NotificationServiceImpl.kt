package jjfactory.diary.domain.notification

import jjfactory.diary.domain.user.UserReader
import jjfactory.diary.infrastructure.notification.NotificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class NotificationServiceImpl(
    private val notificationReader: NotificationReader,
    private val userReader: UserReader,
    private val notificationRepository: NotificationRepository
) : NotificationService {


    @Transactional(readOnly = true)
    override fun getAllNotificationsByTargetId(targetUserId: Long, pageable: Pageable, isRead: Boolean?): Page<NotificationInfo.List> {
        val pages = notificationReader.getPageByTargetUserId(targetUserId, pageable, isRead)

        val sourceUserIds = pages.map {
            it!!.sourceUserId
        }.toList()

        val sourceUserMap = userReader.getByIdIn(sourceUserIds).map {
            it.id to it.username
        }.toMap()

        return pages.map {
            NotificationInfo.List(
                type = it!!.type,
                id = it.id!!,
                targetUserId = it.targetUserId,
                sourceUserId = it.sourceUserId,
                sourceUserName = sourceUserMap[it.sourceUserId] ?: "",
                createdAt = it.createdAt!!
            )
        }

    }


    override fun readAllNotifications(userId: Long) {
        notificationReader.findAllByTargetUserIdAndReadIsFalse(userId).forEach {
            it.read()
        }
    }

    override fun storeCommentWriteNotification(sourceUserId: Long, targetUserId: Long){
        val initNotification = Notification(
            sourceUserId = sourceUserId,
            targetUserId = targetUserId,
            type = NotificationType.WRITE_COMMENT
        )

        notificationRepository.save(initNotification)
    }

    override fun storeFriendRequestNotification(sourceUserId: Long, targetUserId: Long){
        val initNotification = Notification(
            sourceUserId = sourceUserId,
            targetUserId = targetUserId,
            type = NotificationType.FRIEND_REQUEST
        )

        notificationRepository.save(initNotification)
    }
}