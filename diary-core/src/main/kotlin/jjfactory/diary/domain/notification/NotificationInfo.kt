package jjfactory.diary.domain.notification

import java.time.LocalDateTime

class NotificationInfo {
    data class Detail(
        val type: NotificationType,
        val id: Long,
        val targetUserId: Long,
        val sourceUserId: Long,
        val sourceUserName: String,
        val createdAt: LocalDateTime,
    )

    data class List(
        val type: NotificationType,
        val id: Long,
        val targetUserId: Long,
        val sourceUserId: Long,
        val sourceUserName: String,
        val createdAt: LocalDateTime,
    )
}