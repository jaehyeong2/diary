package jjfactory.diary.domain

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class AdminUserServiceImpl(
    private val adminUserReader: AdminUserReader
) : AdminUserService {

    @Transactional(readOnly = true)
    override fun getInfoByUserId(userId: Long): AdminUserInfo.Detail {
        adminUserReader.getOrThrow(userId).let {
            return AdminUserInfo.Detail(
                id = it.id!!,
                lastName = it.lastName,
                firstName = it.firstName,
                phone = it.phone,
                email = it.email,
            )
        }
    }
}