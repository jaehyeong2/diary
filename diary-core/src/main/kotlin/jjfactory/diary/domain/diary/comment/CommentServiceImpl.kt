package jjfactory.diary.domain.diary.comment

import jjfactory.diary.domain.diary.DiaryReader
import jjfactory.diary.domain.user.UserReader
import jjfactory.diary.common.exception.AccessForbiddenException
import jjfactory.diary.infrastructure.diary.comment.CommentRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
class CommentServiceImpl(
    private val commentReader: CommentReader,
    private val userReader: UserReader,
    private val diaryReader: DiaryReader,
    private val commentRepository: CommentRepository
) : CommentService {

    @Transactional(readOnly = true)
    override fun getPageByDiaryId(pageable: Pageable, diaryId: Long): Page<CommentInfo.List?> {
        return commentReader.getPageInfo(pageable, diaryId)
    }

    override fun deleteById(id: Long, userId: Long) {
        if (commentReader.getOrThrow(id).userId != userId) throw AccessForbiddenException()
        commentRepository.deleteById(id)
    }

    override fun modify(id: Long, userId: Long, command: CommentCommand.Modify) {
        val comment = commentReader.getOrThrow(id)
        if (comment.userId != userId) throw AccessForbiddenException()

        comment.modify(command.content, parentId = command.parentId)
    }

    override fun create(userId: Long, command: CommentCommand.Create): Long {
        command.parentId?.let {
            commentReader.getOrThrow(it)
        }

        val user = userReader.getOrThrow(userId)
        val initEntity = command.toEntity(diaryId = command.diaryId, userId = userId)

        val comment = commentRepository.save(initEntity)

        user.pointUp(1)
        return comment.id!!
    }
}