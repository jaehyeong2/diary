package jjfactory.diary.domain

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminUserReader {
    fun get(id: Long): AdminUser?
    fun getOrThrow(id: Long): AdminUser
    fun getByIdIn(ids: List<Long>): List<AdminUser>
    fun getOrThrowByEmail(username: String): AdminUser
}