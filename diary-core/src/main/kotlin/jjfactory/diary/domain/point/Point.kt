package jjfactory.diary.domain.point

import jakarta.persistence.*
import jjfactory.diary.domain.user.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Table(name = "points")
@Entity
class Point(
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @OneToOne(fetch = FetchType.LAZY)
    val user: User,
    var amount: Int = 0,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    @CreationTimestamp
    var createdAt: LocalDateTime? = null
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null

    fun up(amount: Int){
        this.amount += amount
    }

    fun downToZero(amount: Int){
        if (amount > this.amount) this.amount = 0
        else this.amount -= amount
    }

}