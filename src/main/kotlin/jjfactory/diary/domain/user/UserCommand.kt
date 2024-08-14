package jjfactory.diary.domain.user

class UserCommand {
    data class Create(
        val lastName: String,
        val firstName: String,
        val phone: String,
        val password: String,
        var gender: User.Gender,
        val email: String,
        var username: String
    ){
        fun toEntity(encPassword: String): User {
            return User(
                lastName = lastName,
                firstName = firstName,
                phone = phone,
                gender = gender,
                password = encPassword,
                email = email,
                username = username
            )
        }
    }
}