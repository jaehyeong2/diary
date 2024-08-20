package jjfactory.diary.domain

class AdminUserCommand {
    data class Create(
        val lastName: String,
        val firstName: String,
        val phone: String,
        val password: String,
        val email: String,
    ){
        fun toEntity(encPassword: String): AdminUser {
            return AdminUser(
                lastName = lastName,
                firstName = firstName,
                phone = phone,
                password = encPassword,
                email = email,
            )
        }
    }
}