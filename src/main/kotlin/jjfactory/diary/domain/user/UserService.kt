package jjfactory.diary.domain.user

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserService {
    fun getInfoByUserId(userId: Long): UserInfo.Detail
    fun getUserPage(pageable: Pageable): Page<UserInfo.List>
}