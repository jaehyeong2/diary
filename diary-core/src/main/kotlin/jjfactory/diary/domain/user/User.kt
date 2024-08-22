package jjfactory.diary.domain.user

import jakarta.persistence.*
import jjfactory.diary.domain.point.Point
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Table(name = "users")
@Entity
class User(
    val lastName: String,
    val firstName: String,
    val phone: String,
    val password: String,
    @Enumerated(EnumType.STRING)
    var gender: Gender,
    val email: String,
    var username: String,
    var activated: Boolean = false,

    ) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    @CreationTimestamp
    var createdAt: LocalDateTime? = null
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    var point: Point? = null

    var fcmToken: String? = null

    enum class Gender {
        MALE, FEMALE
    }

    fun activate(){
        activated = true
    }

    fun getCurrentPoint(): Int {
        return point?.amount ?: 0
    }

    fun pointUp(amount: Int){
        ;point?.up(amount)
    }

    fun pointDown(amount: Int){
        point?.downToZero(amount)
    }

    private fun validatePoint(amount: Int){
        if (getCurrentPoint() < amount) throw NotEnoughPointException()
    }

    fun sendPoint(amount: Int){
        validatePoint(amount)
        pointDown(amount = amount)
    }
}