package jjfactory.diary.domain.user

class UserCommand {
    data class Create(
        val lastName: String,
        val firstName: String,
        val phone: String,
        var gender: User.Gender,
        val email: String,
        var username: String
    ){
        fun toEntity(): User {
            return User(
                lastName = lastName,
                firstName = firstName,
                phone = phone,
                gender = gender,
                email = email,
                username = username
            )
        }
    }
}