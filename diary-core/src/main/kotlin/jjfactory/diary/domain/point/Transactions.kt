package jjfactory.diary.domain.point

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
class Transactions(
    val senderId: Long,
    val receiverId: Long,
    val amount: Int
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @CreationTimestamp
    var createdAt: LocalDateTime? = null
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null
}