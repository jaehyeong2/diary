package jjfactory.diary.domain.diary.comment

import org.springframework.data.domain.Page

interface CommentService {
    fun getListByUserId(userId: Long): Page<CommentInfo.List>
    fun getCommentById(id: Long): CommentInfo.Detail
    fun deleteById(id: Long, userId: Long)
    fun modify(id: Long, userId: Long, command: CommentCommand.Modify)
    fun create(userId:Long, command: CommentCommand.Create)
}