package jjfactory.diary.domain.auth

import jjfactory.diary.domain.user.UserCommand

interface AuthService {
    fun join(command: UserCommand.Create): Long
    fun login(email: String, password: String): AuthInfo.Detail
}