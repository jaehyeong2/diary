package jjfactory.diary.domain.user

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserReader {
    fun get(id: Long): User?
    fun getOrThrow(id: Long): User
    fun getByIdIn(ids: List<Long>): List<User>
    fun getOrThrowByUsername(username: String): User
    fun getOrThrowByEmail(username: String): User
    fun existsByUsername(username: String): Boolean
    fun getUserPage(pageable: Pageable): Page<User?>
}