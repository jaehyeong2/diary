package jjfactory.diary.domain

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminUserService {
    fun getInfoByUserId(userId: Long): AdminUserInfo.Detail
}