package jjfactory.diary.infrastructure

import jjfactory.diary.domain.AdminUser
import org.springframework.data.jpa.repository.JpaRepository

interface AdminUserRepository : JpaRepository<AdminUser, Long> {
    fun findByEmail(username: String): AdminUser?
    fun findAllByIdIn(ids: List<Long>): List<AdminUser>
}