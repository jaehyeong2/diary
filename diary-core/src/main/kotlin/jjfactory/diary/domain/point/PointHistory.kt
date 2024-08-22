package jjfactory.diary.domain.point

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
class PointHistory(
    val userId: Long,
    @Enumerated(EnumType.STRING)
    val type: Type,
    val amount: Int,
    val sourceId: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    @CreationTimestamp
    var createdAt: LocalDateTime? = null

    enum class Type(val amount: Int? = null) {
        WRITE_COMMENT(1),
        WRITE_DIARY(5),
        SEND,
        RECEIVE
    }
}