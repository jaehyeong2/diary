package jjfactory.diary.common.exception

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ErrorResponseBody(
    val status: Int = HttpStatus.INTERNAL_SERVER_ERROR.value(),
    val errorCode: String? = status.toString(),
    val message: String?,
    val timestamp: LocalDateTime = LocalDateTime.now().withNano(0)
) {

    constructor(error: BizBaseException) : this(
        status = error.status.value(),
        errorCode = error.code,
        message = error.message,
        timestamp = error.timestamp
    )

}
