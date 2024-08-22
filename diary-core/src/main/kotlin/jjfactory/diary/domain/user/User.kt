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
    var activated: Boolean = false,

    ) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    @CreationTimestamp
    var createdAt: LocalDateTime? = null
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null


    var fcmToken: String? = null

    enum class Gender {
        MALE, FEMALE
    }

    fun activate(){
        activated = true
    }
}