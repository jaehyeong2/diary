package jjfactory.diary.domain.auth

import jakarta.servlet.http.HttpServletRequest
import jjfactory.diary.domain.user.UserCommand

interface AuthService {
    fun join(command: UserCommand.Create): Long
    fun login(email: String, password: String): AuthInfo.Detail
    fun authenticate(accessToken: String, request: HttpServletRequest)
}