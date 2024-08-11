package jjfactory.diary.domain.user

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "users")
@Entity
class User(
    val lastName: String,
    val firstName: String,
    val phone: String,
    var gender: Gender,
    val email: String,
    var username: String,
    var point: Int = 0

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

    fun pointDown(point: Int){
        if (this.point < point) this.point = 0
        else this.point -= point
    }
}