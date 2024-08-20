package jjfactory.diary.domain

import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

class AdminUserInfo {
    data class Detail(
        val id: Long,
        val lastName: String,
        val firstName: String,
        val phone: String,
        val email: String,
    )

    data class List(
        val id: Long,
        val lastName: String,
        val firstName: String,
        val phone: String,
        val email: String,
    )
}