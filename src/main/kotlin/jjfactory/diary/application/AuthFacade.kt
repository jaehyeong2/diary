package jjfactory.diary.application

import jjfactory.diary.domain.user.UserCommand
import jjfactory.diary.domain.user.UserService
import jjfactory.diary.infrastructure.supporter.MailSender
import org.springframework.stereotype.Component

@Component
class AuthFacade(
    private val userService: UserService,
    private val mailSender: MailSender
) {
    fun join(command: UserCommand.Create){
        userService.join(command)
        mailSender.sendUserActivateMail(command.email)
    }
}