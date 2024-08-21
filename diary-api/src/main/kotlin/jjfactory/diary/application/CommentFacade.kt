package jjfactory.diary.application

import jjfactory.diary.domain.diary.DiaryService
import jjfactory.diary.domain.diary.comment.CommentCommand
import jjfactory.diary.domain.diary.comment.CommentService
import jjfactory.diary.domain.notification.NotificationService
import jjfactory.diary.domain.notification.NotificationType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CommentFacade(
    private val commentService: CommentService,
    private val diaryService: DiaryService,
    private val notificationService: NotificationService
) {

    @Transactional
    fun writeComment(userId: Long, command: CommentCommand.Create): Long {
        val commentId = commentService.create(userId = userId, command = command)
        val diary = diaryService.getDiary(id = command.diaryId, userId = userId)

        notificationService.store(
            sourceUserId = userId,
            targetUserId = diary.userId,
            type = NotificationType.WRITE_COMMENT
        )

        return commentId
    }
}