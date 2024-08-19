package jjfactory.diary.infrastructure.diary.comment

import jjfactory.diary.domain.diary.Diary
import jjfactory.diary.domain.diary.comment.Comment
import jjfactory.diary.domain.diary.comment.CommentReader
import jjfactory.diary.common.exception.ResourceNotFoundException
import jjfactory.diary.domain.diary.comment.CommentInfo
import jjfactory.diary.domain.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    override fun getPage(pageable: Pageable, diaryId: Long): Page<Comment?> {
        return commentRepository.findPage(pageable) {
            select(
                entity(Comment::class)
            ).from(
                entity(Comment::class),
                join(User::class).on(path(Comment::userId).eq(path(User::id))),
//                leftJoin(Comment::class).on(path(Comment::parentId).eq(path(Comment::id)))
            ).whereAnd(
                path(Comment::diaryId).eq(diaryId),
            ).orderBy(
                path(Comment::parentId).asc().nullsFirst()
            )
        }
    }

    override fun getPageInfo(pageable: Pageable, diaryId: Long): Page<CommentInfo.List?> {
        return commentRepository.findPage(pageable) {
            selectNew<CommentInfo.List>(
                path(Comment::id),
                path(Comment::diaryId),
                path(Comment::parentId),
                path(Comment::userId),
                path(User::username),
                path(Comment::content),
                path(Comment::createdAt),
                path(Comment::updatedAt),
            ).from(
                entity(Comment::class),
                join(User::class).on(path(Comment::userId).eq(path(User::id))),
//                leftJoin(Comment::class).on(path(Comment::parentId).eq(path(Comment::id)))
            ).where(
                path(Comment::diaryId).eq(diaryId),
            ).orderBy(
                path(Comment::parentId).asc().nullsFirst()
            )
        }
    }
}