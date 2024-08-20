package jjfactory.diary.infrastructure

import jjfactory.diary.domain.user.User
import jjfactory.diary.domain.user.UserReader
import jjfactory.diary.common.exception.ResourceNotFoundException
import jjfactory.diary.domain.AdminUser
import jjfactory.diary.domain.AdminUserReader
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class AdminUserReaderImpl(
    private val adminUserRepository: AdminUserRepository
) : AdminUserReader {
    override fun get(id: Long): AdminUser? {
        return adminUserRepository.findByIdOrNull(id)
    }

    override fun getOrThrow(id: Long): AdminUser {
        return adminUserRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("user not found")
    }

    override fun getByIdIn(ids: List<Long>): List<AdminUser> {
        return adminUserRepository.findAllByIdIn(ids)
    }

    override fun getOrThrowByEmail(username: String): AdminUser {
        return adminUserRepository.findByEmail(username) ?: throw ResourceNotFoundException("user not found")
    }
}