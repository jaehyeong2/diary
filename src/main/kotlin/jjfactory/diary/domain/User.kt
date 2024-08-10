package jjfactory.diary.domain

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
    var username: String
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    enum class Gender {
        MSLE, FEMALE
    }
}