package jjfactory.diary.domain.user

interface UserService {
    fun getInfoByUserId(userId: Long): UserInfo.Detail
}