package jjfactory.diary.domain.auth

import java.time.LocalDateTime

class AuthInfo {
    data class Detail(
        val accessToken: Token,
        val refreshToken: Token,
    )

    data class Token(
        val value: String,
        val expiredAt: LocalDateTime
    )
}