package jjfactory.diary.domain.auth

import io.jsonwebtoken.Claims
import jakarta.servlet.http.HttpServletRequest
import jjfactory.diary.common.exception.BadCredentialsException
import jjfactory.diary.config.security.JwtProvider
import jjfactory.diary.config.security.UserAuthentication
import jjfactory.diary.domain.user.DuplicateUserNameException
import jjfactory.diary.domain.user.UserCommand
import jjfactory.diary.domain.user.UserReader
import jjfactory.diary.infrastructure.user.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.time.LocalDateTime
import kotlin.math.exp

@Component
class AuthServiceImpl(
    private val userReader: UserReader,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) : AuthService {

    @Value("\${security.jwt.token.access-token-span}")
    lateinit var ACCESS_TOKEN_LIFESPAN: String

    private val REFRESH_TOKEN_LIFESPAN = 60 * 60 * 24 * 90

    @Transactional
    override fun join(command: UserCommand.Create): Long {
        if (userRepository.existsByUsername(command.username)) throw DuplicateUserNameException()
        val encPassword = passwordEncoder.encode(command.password)

        val initUser = command.toEntity(encPassword)
        val user = userRepository.save(initUser)
        return user.id!!
    }

    override fun login(email: String, password: String): AuthInfo.Detail {
        val user = userReader.getOrThrowByEmail(email)
        if (!passwordEncoder.matches(password, user.password)) throw BadCredentialsException()

        return AuthInfo.Detail(
            accessToken = generateAccessToken(userId = user.id!!),
            refreshToken = generateRefreshToken(userId = user.id!!)
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

        validateToken(jwtData)

        val isAuthenticated = UserAuthentication(token = accessToken, claims = jwtData)
        SecurityContextHolder.getContext().authentication = isAuthenticated
    }

    private fun validateToken(jwtData: Claims) {
//        val isValidToken = managerAuthKeyRepository.existsByIdAndTokenAndExpireDtGreaterThan(
//            jwtData.authKeyId, jwtData.token, LocalDateTime.now()
//        )
//
//        if (!isValidToken) throw InvalidAccessTokenException()
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