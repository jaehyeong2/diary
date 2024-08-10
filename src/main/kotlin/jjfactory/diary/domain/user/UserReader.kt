package jjfactory.diary.domain.user

interface UserReader {
    fun get(id: Long): User?
    fun getOrThrow(id: Long): User
}