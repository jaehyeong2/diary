package jjfactory.diary.domain.auth

import jjfactory.diary.domain.user.DuplicateUserNameException
import jjfactory.diary.domain.user.UserCommand
import jjfactory.diary.domain.user.UserReader
import jjfactory.diary.infrastructure.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AuthServiceImpl(
    private val userReader: UserReader,
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) : AuthService {
    override fun join(command: UserCommand.Create): Long {
        if (userRepository.existsByUsername(command.username)) throw DuplicateUserNameException()
        val encPassword = passwordEncoder.encode(command.password)

        val initUser = command.toEntity(encPassword)
        val user = userRepository.save(initUser)
        return user.id!!
    }

    override fun login(email: String, password: String) {
        TODO("Not yet implemented")
    }
}