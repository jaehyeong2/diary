package jjfactory.diary.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val message: String,
    val code: String,
    val status: HttpStatus? = HttpStatus.INTERNAL_SERVER_ERROR
) {

    /*
     * Common Error Code
     * Use prefix with 'C'
     */
    BAD_REQUEST_ERROR("잘못된 요청 입니다", "C0004", HttpStatus.BAD_REQUEST),
    BAD_INPUT_ERROR("입력에 오류가 있습니다", "C0003", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_PRIVILEGE_ERROR("권한이 불충분 합니다", "C0002", HttpStatus.BAD_REQUEST),
    UN_AUTHORIZED_ERROR("인증되지 않는 접근입니다", "C0001", HttpStatus.UNAUTHORIZED),
    UNKNOWN_ERROR("알수 없는 오류가 발생했습니다. 잠시 후 다시 시도해 주세요", "C9999", HttpStatus.INTERNAL_SERVER_ERROR),

    /*
     * 인증 관련 오류
     * Use prefix with 'A'
     */
    CREDENTIAL_INVALID_ERROR("Toast.Login.ErrorDesc.IncorrectPassword", "A", HttpStatus.UNAUTHORIZED),
    CREDENTIAL_NOT_MATCH_ERROR("Manager.PasswordSettings.ErrorMsg.CurrentPasswordIncorrect", "0004", HttpStatus.BAD_REQUEST),

    /**
     * 계정 관련 오류
     * Use prefix with 'AC'
     */
    ACCOUNT_NOT_FOUND_ERROR("계정 정보가 없습니다", "5000", HttpStatus.BAD_REQUEST),

    CONFLICT_DUPLICATE_USERNAME("중복된 유저네임입니다.", "0001", HttpStatus.CONFLICT)
}