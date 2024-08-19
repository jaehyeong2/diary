package jjfactory.diary.domain.diary

import java.time.LocalDateTime

class DiaryInfo {
    data class Detail(
        val id: Long,
        var type: Diary.Type,
        var content: String,
        val title: String,
        val userId: Long,
        val username: String,
        var createdAt: LocalDateTime? = null,
        var updatedAt: LocalDateTime? = null,
    )

    data class List(
        val id: Long,
        var type: Diary.Type,
        val userId: Long,
        val title: String,
        val username: String,
        var createdAt: LocalDateTime? = null,
        var updatedAt: LocalDateTime? = null,
    )
}