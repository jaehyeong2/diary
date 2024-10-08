package jjfactory.diary.common.exception

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {
    val logger = LoggerFactory.getLogger(CustomExceptionHandler::class.java)

    @Value("\${application.Environment}")
    private lateinit var environment: String

    @ExceptionHandler(BizBaseException::class)
    fun handleBizException(ex: BizBaseException): ResponseEntity<ErrorResponseBody> {
        return ResponseEntity(ErrorResponseBody(ex), HttpStatus.CONFLICT)
    }

    @ExceptionHandler(AccessForbiddenException::class)
    fun handleAccessForbiddenException(
        ex: AccessForbiddenException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponseBody> {
        val errorResponse = ErrorResponseBody(
            status = HttpStatus.FORBIDDEN.value(),
            message = "has no access for requested resource",
        )
        return ResponseEntity(errorResponse, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(UnAuthorizedException::class)
    fun handleUnAuthorizedException(
        ex: UnAuthorizedException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponseBody> {
        val errorResponse = ErrorResponseBody(
            status = HttpStatus.UNAUTHORIZED.value(),
            message = ex.message,
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(
        ex: ResourceNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponseBody> {
        val errorResponse = ErrorResponseBody(
            status = HttpStatus.NOT_FOUND.value(),
            message = ex.message
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        ex: HttpMessageNotReadableException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponseBody> {
        val errorResponse = ErrorResponseBody(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
}