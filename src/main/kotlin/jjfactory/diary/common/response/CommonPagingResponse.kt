package jjfactory.diary.common.response

import jjfactory.diary.domain.user.UserInfo
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import java.time.LocalDateTime


class CommonPagingResponse<T>(
    var status: Int = HttpStatus.OK.value(),
    var data: PagingResponse<T>,
    val responseTime: LocalDateTime = LocalDateTime.now(),
) {
    constructor(data: PagingResponse<T>) : this(
        status = HttpStatus.OK.value(),
        data = data,
        responseTime = LocalDateTime.now(),
    )
}

data class PagingResponse<T>(
    val currentPage: Int = 0,
    val totalPage: Int = 0,
    val totalCount: Long = 0L,
    val dataList: List<T>?
) {
    constructor(list: Page<T>) : this(
        dataList = list.content,
        currentPage = list.pageable.pageNumber + 1,
        totalCount = list.totalElements,
        totalPage = list.totalPages
    )
}