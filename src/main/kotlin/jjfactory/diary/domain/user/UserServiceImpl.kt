package jjfactory.diary.domain.user

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserServiceImpl(
    private val userReader: UserReader,
) : UserService {

    @Transactional(readOnly = true)
    override fun getInfoByUserId(userId: Long): UserInfo.Detail {
        userReader.getOrThrow(userId).let {
            return UserInfo.Detail(
                id = it.id!!,
                lastName = it.lastName,
                firstName = it.firstName,
                phone = it.phone,
                gender = it.gender.toString(),
                email = it.email,
                username = it.username,
                point = it.point
            )
        }
    }

    @Transactional(readOnly = true)
    override fun getUserPage(pageable: Pageable): Page<UserInfo.List> {
        return userReader.getUserPage(pageable).map {
            UserInfo.List(
                id = it?.id!!,
                lastName = it.lastName,
                firstName = it.firstName,
                phone = it.phone,
                gender = it.gender.toString(),
                email = it.email,
                username = it.username,
                point = it.point
            )
        }
    }
}