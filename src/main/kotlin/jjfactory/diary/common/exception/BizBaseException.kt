package jjfactory.diary.common.exception

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

open class BizBaseException(
    val status: HttpStatus,
    val code: String,
    override val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now().withNano(0)
): RuntimeException() {

    constructor(errorCode: ErrorCode) : this(
        status = errorCode.status!!,
        code = errorCode.code,
        message = errorCode.message ?: ""
    )
}