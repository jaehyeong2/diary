package jjfactory.diary.domain.user

import jjfactory.diary.infrastructure.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    @Transactional
    override fun join(command: UserCommand.Create): Long {
        val initUser = command.toEntity()
        val user = userRepository.save(initUser)
        return user.id!!
    }


}