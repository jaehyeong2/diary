package jjfactory.diary.presentation

import jjfactory.diary.application.AuthFacade
import jjfactory.diary.common.response.CommonResponse
import jjfactory.diary.domain.auth.AuthInfo
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

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): CommonResponse<AuthInfo.Detail> {
        val result = authFacade.login(
            email = request.email,
            password = request.password
        )

        return CommonResponse(result)
    }

}

data class LoginRequest(
    val email: String,
    val password: String
)