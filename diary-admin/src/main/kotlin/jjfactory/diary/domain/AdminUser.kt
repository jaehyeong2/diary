package jjfactory.diary.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class AdminUser(
    val lastName: String,
    val firstName: String,
    val phone: String,
    val password: String,
    val email: String,
    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}