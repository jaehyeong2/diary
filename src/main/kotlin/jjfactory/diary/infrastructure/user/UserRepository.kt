package jjfactory.diary.infrastructure.user

import jjfactory.diary.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): User?
    fun findByEmail(username: String): User?

    fun findAllByIdIn(ids: List<Long>): List<User>
}