package jjfactory.diary.domain.user

interface UserReader {
    fun get(id: Long): User?
    fun getOrThrow(id: Long): User
    fun getOrThrowByUsername(username: String): User
    fun getOrThrowByEmail(username: String): User
    fun existsByUsername(username: String): Boolean
}