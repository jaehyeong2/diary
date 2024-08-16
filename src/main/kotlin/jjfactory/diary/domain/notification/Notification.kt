package jjfactory.diary.domain.notification

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
class Notification(
    @Enumerated(EnumType.STRING)
    val type: NotificationType,

    val sourceUserId: Long,
    val targetUserId: Long,

    var isRead: Boolean = false,

    @CreationTimestamp
    var createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun read() {
        isRead = true
    }
}