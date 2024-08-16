package jjfactory.diary.domain.friend

import jakarta.persistence.*
import jjfactory.diary.common.exception.AccessForbiddenException
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
class Friend(
    val senderId: Long,
    val receiverId: Long,

    @Enumerated(EnumType.STRING)
    var status: Status = Status.REQUESTED,

    @CreationTimestamp
    var createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    enum class Status {
        REQUESTED, ACCEPTED
    }

    fun accept(userId: Long) {
        if (receiverId != userId) throw AccessForbiddenException()
        status = Status.ACCEPTED
    }
}