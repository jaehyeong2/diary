package jjfactory.diary.domain.diary.comment

import java.time.LocalDateTime

class CommentInfo {
    data class List(
        val id: Long,
        val diaryId: Long,
        val parentId: Long? = null,
        val userId: Long,
        val userName: String,
        val content: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
    )

    data class Detail(
        val id: Long,
        val diaryId: Long,
        val userId: Long,
        val userName: String,
        val content: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
    )
}