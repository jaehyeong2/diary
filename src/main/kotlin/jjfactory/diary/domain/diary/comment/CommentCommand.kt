package jjfactory.diary.domain.diary.comment

import jjfactory.diary.domain.diary.Diary
import jjfactory.diary.domain.user.User

class CommentCommand {
    data class Create(
        val diaryId: Long,
        val content: String
    ){
        fun toEntity(diary: Diary, user: User): Comment {
            return Comment(
                user = user,
                diary = diary,
                content = content,
            )
        }
    }

    data class Modify(
        val diaryId: Long,
        val content: String
    )
}