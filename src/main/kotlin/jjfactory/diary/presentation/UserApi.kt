package jjfactory.diary.presentation

import io.swagger.v3.oas.annotations.Operation
import jjfactory.diary.common.response.CommonResponse
import jjfactory.diary.config.security.AuthSupporter
import jjfactory.diary.config.security.UserAuthentication
import jjfactory.diary.domain.friend.FriendService
import jjfactory.diary.domain.user.UserInfo
import jjfactory.diary.domain.user.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/users")
@RestController
class UserApi(
    private val userService: UserService,
) {

    @Operation(summary = "내 정보 조회")
    @GetMapping("/my")
    fun getMyInfo(): CommonResponse<UserInfo.Detail> {
        val userId = AuthSupporter.getLoginUserId()

        return CommonResponse(
            userService.getInfoByUserId(userId)
        )
    }
}