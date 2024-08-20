package jjfactory.diary.domain.auth

import jakarta.servlet.http.HttpServletRequest
import jjfactory.diary.domain.AdminUserCommand
import jjfactory.diary.domain.user.UserCommand

interface AuthService {
    fun login(email: String, password: String): AuthInfo.Detail
    fun authenticate(accessToken: String, request: HttpServletRequest)
    fun join(command: AdminUserCommand.Create): Long
}