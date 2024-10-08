package jjfactory.diary.domain.user

import jakarta.persistence.*
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
    var point: Int = 0,
    var activated: Boolean = false,

    @CreationTimestamp
    var createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,

    ) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    var fcmToken: String? = null

    enum class Gender {
        MALE, FEMALE
    }

    fun pointUp(point: Int){
        this.point += point
    }

    fun pointDownForTransaction(point: Int){
        if (this.point < point) throw NotEnoughPointException()
        pointDown(point)
    }

    fun pointDown(point: Int){
        if (this.point < point) this.point = 0
        else this.point -= point
    }

    fun activate(){
        activated = true
    }
}