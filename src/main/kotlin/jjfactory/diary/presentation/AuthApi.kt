package jjfactory.diary.presentation

import jjfactory.diary.application.AuthFacade
import jjfactory.diary.common.response.CommonResponse
import jjfactory.diary.domain.user.UserCommand
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthApi(
    private val authFacade: AuthFacade
) {
    @PostMapping("/join")
    fun signUp(@RequestBody command: UserCommand.Create): CommonResponse<Unit> {
        return CommonResponse(authFacade.join(command))
    }

}