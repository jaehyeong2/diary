package jjfactory.diary.infrastructure.user

import jjfactory.diary.domain.user.User
import jjfactory.diary.domain.user.UserReader
import jjfactory.diary.exception.ResourceNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserReaderImpl(
    private val userRepository: UserRepository
) : UserReader {
    override fun get(id: Long): User? {
        return userRepository.findByIdOrNull(id)
    }

    override fun getOrThrow(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("user not found")
    }
}