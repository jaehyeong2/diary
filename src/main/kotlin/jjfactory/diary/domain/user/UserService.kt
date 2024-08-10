package jjfactory.diary.domain.user

interface UserService {
    fun join(command: UserCommand.Create): Long
}