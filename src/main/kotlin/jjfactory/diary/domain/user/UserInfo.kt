package jjfactory.diary.domain.user

import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

class UserInfo {
    data class Detail(
        val lastName: String,
        val firstName: String,
        val phone: String,
        val gender: String,
        val email: String,
        val username: String,
        val point: Int,
    )

    data class List(
        val lastName: String,
        val firstName: String,
        val phone: String,
        val gender: String,
        val email: String,
        val username: String,
        val point: Int,
    )
}