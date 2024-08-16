package jjfactory.diary.presentation

import jjfactory.diary.common.response.CommonResponse
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
    @GetMapping("/my")
    fun getMyInfo(): CommonResponse<UserInfo.Detail> {
        val userAuthentication = SecurityContextHolder.getContext().authentication as UserAuthentication

        return CommonResponse(
            userService.getInfoByUserId(userAuthentication.getUserId())
        )
    }
}