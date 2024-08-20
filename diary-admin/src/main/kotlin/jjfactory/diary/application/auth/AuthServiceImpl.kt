package jjfactory.diary.domain.auth

import io.jsonwebtoken.Claims
import jakarta.servlet.http.HttpServletRequest
import jjfactory.diary.common.exception.BadCredentialsException
import jjfactory.diary.config.security.JwtProvider
import jjfactory.diary.config.security.UserAuthentication
import jjfactory.diary.domain.AdminUser
import jjfactory.diary.domain.AdminUserCommand
import jjfactory.diary.domain.AdminUserReader
import jjfactory.diary.domain.user.DuplicateUserNameException
import jjfactory.diary.domain.user.UserCommand
import jjfactory.diary.infrastructure.AdminUserRepository
import jjfactory.diary.infrastructure.user.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.time.LocalDateTime

@Component
class AuthServiceImpl(
    private val adminUserReader: AdminUserReader,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder,
    private val adminUserRepository: AdminUserRepository
) : AuthService {

    @Value("\${security.jwt.token.access-token-span}")
    lateinit var ACCESS_TOKEN_LIFESPAN: String

    private val REFRESH_TOKEN_LIFESPAN = 60 * 60 * 24 * 90

    @Transactional
    override fun join(command: AdminUserCommand.Create): Long {
        val encPassword = passwordEncoder.encode(command.password)

        val initUser = command.toEntity(encPassword)
        val user = adminUserRepository.save(initUser)
        return user.id!!
    }

    override fun login(email: String, password: String): AuthInfo.Detail {
        val adminUser = adminUserReader.getOrThrowByEmail(email)
        if (!passwordEncoder.matches(password, adminUser.password)) throw BadCredentialsException()

        return AuthInfo.Detail(
            accessToken = generateAccessToken(userId = adminUser.id!!),
            refreshToken = generateRefreshToken(userId = adminUser.id)
        )
    }

    private fun generateAccessToken(userId: Long): AuthInfo.Token {
        val expiredAt = LocalDateTime.now().plusSeconds(ACCESS_TOKEN_LIFESPAN.toLong())
        val token =
            jwtProvider.create(
                type = "ACCESS_TOKEN",
                userId = userId,
                Timestamp.valueOf(expiredAt)
            )

        return AuthInfo.Token(
            value = token,
            expiredAt = expiredAt
        )
    }

    override fun authenticate(accessToken: String, request: HttpServletRequest) {
        // 토큰 정보 취득
        val jwtData = jwtProvider.extract(accessToken)

        val isAuthenticated = UserAuthentication(token = accessToken, claims = jwtData)
        SecurityContextHolder.getContext().authentication = isAuthenticated
    }

    private fun generateRefreshToken(userId: Long): AuthInfo.Token {
        val expiredAt = LocalDateTime.now().plusSeconds(REFRESH_TOKEN_LIFESPAN.toLong())
        val token = jwtProvider.create("REFRESH_TOKEN", userId, null)
        return AuthInfo.Token(
            value = token,
            expiredAt = expiredAt
        )
    }

}