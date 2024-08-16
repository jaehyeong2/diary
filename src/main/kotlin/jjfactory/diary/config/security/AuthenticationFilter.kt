package jjfactory.diary.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jjfactory.diary.domain.auth.AuthService
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthenticationFilter(
    private val authService: AuthService
) : OncePerRequestFilter() {

    private val BEARER_TOKEN_KEY_NAME = "Authorization"
    private val BEARER_TOKEN_PREFIX = "Bearer "

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        authenticate(request)
        filterChain.doFilter(request, response)
    }

    fun authenticate(request: HttpServletRequest) {
        if (request.method.equals(HttpMethod.OPTIONS)) {
            return
        }

        // 토큰이 있는지 체크
        var token = request.getHeader(BEARER_TOKEN_KEY_NAME)

        // 없을 시 continue
        if (token.isNullOrEmpty() || "null".equals(token, ignoreCase = true)) {
            return
        }

        // 토큰으로 유저 조회 후 authenticate 진행
        token = token.replace(BEARER_TOKEN_PREFIX, "")

        authService.authenticate(
            accessToken = token,
            request = request
        )

    }
}