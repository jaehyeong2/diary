package jjfactory.diary.domain.diary

import jjfactory.diary.domain.user.User

class DiaryCommand {
    data class Create(
        val content: String,
        val title: String,
        val type: Diary.Type,
        val accessLevel: Diary.AccessLevel
    ){
        fun toEntity(userId: Long): Diary {
            return Diary(
                userId = userId,
                type = type,
                title = title,
                content = content,
                accessLevel = accessLevel
            )
        }
    }

    data class Modify(
        val content: String,
        val title: String,
        val type: Diary.Type,
        val accessLevel: Diary.AccessLevel
    )

    data class Report(
        val reason: String
    )
}