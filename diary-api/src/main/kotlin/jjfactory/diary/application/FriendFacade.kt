package jjfactory.diary.application

import jjfactory.diary.domain.friend.FriendService
import jjfactory.diary.domain.notification.NotificationService
import jjfactory.diary.domain.notification.NotificationType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FriendFacade(
    private val friendService: FriendService,
    private val notificationService: NotificationService
) {

    @Transactional
    fun sendRequest(senderId: Long, receiverId: Long): Long {
        val friendId = friendService.sendRequest(
            senderId = senderId,
            receiverId = receiverId
        )

        notificationService.store(
            sourceUserId = senderId,
            targetUserId = receiverId,
            type = NotificationType.FRIEND_REQUEST
        )

        return friendId
    }
}