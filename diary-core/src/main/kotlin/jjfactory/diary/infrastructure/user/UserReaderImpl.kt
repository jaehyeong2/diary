package jjfactory.diary.infrastructure.user

import jjfactory.diary.domain.user.User
import jjfactory.diary.domain.user.UserReader
import jjfactory.diary.common.exception.ResourceNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    override fun getByIdIn(ids: List<Long>): List<User> {
        return userRepository.findAllByIdIn(ids)
    }

    override fun existsByUsername(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }

    override fun getUserPage(pageable: Pageable): Page<User?> {
        return userRepository.findPage(pageable) {
            select(
                entity(User::class)
            ).from(
                entity(User::class)
            )
        }
    }

    override fun getOrThrowByUsername(username: String): User {
        return userRepository.findByUsername(username) ?: throw ResourceNotFoundException("user not found")
    }

    override fun getOrThrowByEmail(username: String): User {
        return userRepository.findByEmail(username) ?: throw ResourceNotFoundException("user not found")
    }
}