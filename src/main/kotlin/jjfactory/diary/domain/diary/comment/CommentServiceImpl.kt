package jjfactory.diary.domain.diary.comment

import jjfactory.diary.infrastructure.diary.comment.CommentRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class CommentServiceImpl(
    private val commentReader: CommentReader,
    private val commentRepository: CommentRepository
) : CommentService {
    override fun getListByUserId(userId: Long): Page<CommentInfo.List> {
        TODO("Not yet implemented")
    }

    override fun getCommentById(id: Long): CommentInfo.Detail {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long, userId: Long) {
        TODO("Not yet implemented")
    }

    override fun modify(id: Long, userId: Long, command: CommentCommand.Modify) {
        TODO("Not yet implemented")
    }

    override fun create(userId: Long, command: CommentCommand.Create) {
        TODO("Not yet implemented")
    }
}