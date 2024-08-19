package jjfactory.diary.domain.diary.comment

import jjfactory.diary.domain.diary.Diary
import jjfactory.diary.domain.user.User

class CommentCommand {
    data class Create(
        val parentId: Long? = null,
        val diaryId: Long,
        val content: String
    ){
        fun toEntity(diaryId: Long, userId: Long): Comment {
            return Comment(
                userId = userId,
                diaryId = diaryId,
                content = content,
                parentId = parentId
            )
        }
    }

    data class Modify(
        val parentId: Long? = null,
        val diaryId: Long,
        val content: String
    )
}