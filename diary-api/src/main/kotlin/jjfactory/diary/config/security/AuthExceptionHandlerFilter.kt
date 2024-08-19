package jjfactory.diary.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jjfactory.diary.common.exception.ErrorCode
import jjfactory.diary.common.exception.ErrorResponseBody
import jjfactory.diary.common.exception.InvalidAccessTokenException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthExceptionHandlerFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: ExpiredJwtException) {
            val errorResponse = ErrorResponseBody(
                status = HttpStatus.UNAUTHORIZED.value(),
                errorCode = ErrorCode.AUTH_EXPIRED_ACCESS_TOKEN.code,
                message = ErrorCode.AUTH_EXPIRED_ACCESS_TOKEN.message
            )

            response.status = HttpStatus.UNAUTHORIZED.value()
            response.contentType = "application/json;charset=UTF-8"
            response.writer.write(objectMapper.writeValueAsString(errorResponse))

        } catch (e: InvalidAccessTokenException) {
            val errorResponse = ErrorResponseBody(
                status = HttpStatus.UNAUTHORIZED.value(),
                errorCode = ErrorCode.AUTH_INVALID_ACCESS_TOKEN.code,
                message = ErrorCode.AUTH_INVALID_ACCESS_TOKEN.message
            )

            e.printStackTrace()

            response.status = HttpStatus.UNAUTHORIZED.value()
            response.contentType = "application/json;charset=UTF-8"
            response.writer.write(objectMapper.writeValueAsString(errorResponse))
        }
    }
}