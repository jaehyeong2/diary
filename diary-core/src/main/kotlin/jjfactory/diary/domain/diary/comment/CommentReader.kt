package jjfactory.diary.domain.diary.comment

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CommentReader {
    fun get(id: Long): Comment?
    fun getOrThrow(id: Long): Comment
    fun getPage(pageable: Pageable, diaryId: Long): Page<Comment?>
    fun getPageInfo(pageable: Pageable, diaryId: Long): Page<CommentInfo.List?>
}