package jjfactory.diary.infrastructure.diary.comment

import jjfactory.diary.domain.diary.Diary
import jjfactory.diary.domain.diary.comment.Comment
import jjfactory.diary.domain.diary.comment.CommentReader
import jjfactory.diary.common.exception.ResourceNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class CommentReaderImpl(
    private val commentRepository: CommentRepository
) : CommentReader {
    override fun get(id: Long): Comment? {
        return commentRepository.findByIdOrNull(id)
    }

    override fun getOrThrow(id: Long): Comment {
        return commentRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("comment not found")
    }
}