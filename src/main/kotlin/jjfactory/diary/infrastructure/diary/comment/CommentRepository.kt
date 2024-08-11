package jjfactory.diary.infrastructure.diary.comment

import jjfactory.diary.domain.diary.Diary
import jjfactory.diary.domain.diary.comment.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
}