package jjfactory.diary.domain.user

import jjfactory.diary.infrastructure.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    @Transactional
    override fun join(command: UserCommand.Create): Long {
        val encPassword = passwordEncoder.encode(command.password)

        val initUser = command.toEntity(encPassword)
        val user = userRepository.save(initUser)
        return user.id!!
    }


}