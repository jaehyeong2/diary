package jjfactory.diary.application

import jjfactory.diary.domain.diary.comment.CommentCommand
import jjfactory.diary.domain.diary.comment.CommentService
import jjfactory.diary.domain.notification.Notification
import jjfactory.diary.domain.notification.NotificationType
import jjfactory.diary.infrastructure.notification.NotificationRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CommentFacade(
    private val commentService: CommentService,
    private val notificationRepository: NotificationRepository
) {

    @Transactional
    fun writeComment(userId: Long, command: CommentCommand.Create): Long {
        val commentId = commentService.create(userId = userId, command = command)

        val initNotification = Notification(
            sourceUserId = userId,
            targetUserId = command.diaryOwnerId,
            type = NotificationType.WRITE_COMMENT
        )

        notificationRepository.save(initNotification)

        return commentId
    }
}