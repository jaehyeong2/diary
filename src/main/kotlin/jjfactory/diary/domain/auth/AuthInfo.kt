package jjfactory.diary.domain.auth

class AuthInfo {
    data class Detail(
        val accessToken: String,
        val refreshToken: String,
    )
}