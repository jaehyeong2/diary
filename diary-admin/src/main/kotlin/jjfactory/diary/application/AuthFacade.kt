package jjfactory.diary.application

import jjfactory.diary.domain.AdminUserCommand
import jjfactory.diary.domain.auth.AuthInfo
import jjfactory.diary.domain.auth.AuthService
import jjfactory.diary.infrastructure.supporter.MailSender
import org.springframework.stereotype.Component

@Component
class AuthFacade(
    private val authService: AuthService,
    private val mailSender: MailSender
) {
    fun join(command: AdminUserCommand.Create) {
        authService.join(command)
        mailSender.sendUserActivateMail(command.email)
    }

    fun login(email: String, password: String): AuthInfo.Detail {
        return authService.login(
            email = email,
            password = password
        )
    }
}