package jjfactory.diary.domain.diary

import jjfactory.diary.domain.user.User

class DiaryCommand {
    data class Create(
        val content: String,
        val type: Diary.Type,
        val accessLevel: Diary.AccessLevel
    ){
        fun toEntity(userId: Long): Diary {
            return Diary(
                userId = userId,
                type = type,
                content = content,
                accessLevel = accessLevel
            )
        }
    }

    data class Modify(
        val content: String,
        val type: Diary.Type,
        val accessLevel: Diary.AccessLevel
    )
}