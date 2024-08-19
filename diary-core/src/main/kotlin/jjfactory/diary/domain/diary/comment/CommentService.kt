package jjfactory.diary.domain.diary.comment

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CommentService {
    fun deleteById(id: Long, userId: Long)
    fun modify(id: Long, userId: Long, command: CommentCommand.Modify)
    fun create(userId:Long, command: CommentCommand.Create): Long
    fun getPageByDiaryId(pageable: Pageable, diaryId: Long): Page<CommentInfo.List?>
}