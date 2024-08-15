package jjfactory.diary.presentation

import jjfactory.diary.domain.friend.FriendService
import jjfactory.diary.domain.user.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/users")
@RestController
class UserApi(
    private val userService: UserService,
    private val friendService: FriendService
) {
}