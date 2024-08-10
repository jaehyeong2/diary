package jjfactory.diary.domain.diary

import jjfactory.diary.domain.user.User

class DiaryCommand {
    data class Create(
        val content: String,
        val type: Diary.Type
    ){
        fun toEntity(user: User): Diary {
            return Diary(
                user = user,
                type = type,
                content = content
            )
        }
    }

    data class Modify(
        val content: String,
        val type: Diary.Type
    )
}