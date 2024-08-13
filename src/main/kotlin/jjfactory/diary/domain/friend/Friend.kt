package jjfactory.diary.domain.friend

import jakarta.persistence.*
import jjfactory.diary.domain.user.User
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
class Friend(
    @ManyToOne(fetch = FetchType.LAZY)
    val sender: User,

    @ManyToOne(fetch = FetchType.LAZY)
    val receiver: User,

    @Enumerated(EnumType.STRING)
    var status: Status = Status.REQUESTED,

    @CreationTimestamp
    var createdAt: LocalDateTime? = null,
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    enum class Status {
        REQUESTED, ACCEPTED
    }

    fun accept(){
        status = Status.ACCEPTED
    }
}