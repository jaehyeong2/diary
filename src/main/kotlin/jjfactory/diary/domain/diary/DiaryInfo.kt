package jjfactory.diary.domain.diary

import java.time.LocalDateTime

class DiaryInfo {
    data class Detail(
        val id: Long,
        var type: Diary.Type,
        var content: String,
        val userId: Long,
        val username: String,
        var createdAt: LocalDateTime? = null,
        var updatedAt: LocalDateTime? = null,
    )
}