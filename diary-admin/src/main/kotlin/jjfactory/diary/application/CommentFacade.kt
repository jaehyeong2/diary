package jjfactory.diary.application

import jjfactory.diary.domain.diary.DiaryService
import jjfactory.diary.domain.diary.comment.CommentCommand
import jjfactory.diary.domain.diary.comment.CommentService
import jjfactory.diary.domain.notification.NotificationService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CommentFacade(
    private val commentService: CommentService,
    private val diaryService: DiaryService,
    private val notificationService: NotificationService
) {


}