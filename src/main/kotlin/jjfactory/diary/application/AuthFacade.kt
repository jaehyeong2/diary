package jjfactory.diary.application

import jjfactory.diary.domain.auth.AuthService
import jjfactory.diary.domain.user.UserCommand
import jjfactory.diary.domain.user.UserService
import jjfactory.diary.infrastructure.supporter.MailSender
import org.springframework.stereotype.Component

@Component
class AuthFacade(
    private val authService: AuthService,
    private val mailSender: MailSender
) {
    fun join(command: UserCommand.Create) {
        authService.join(command)
        mailSender.sendUserActivateMail(command.email)
    }

    fun login(email: String, password: String) {

    }
}