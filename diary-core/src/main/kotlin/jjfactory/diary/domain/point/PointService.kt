package jjfactory.diary.domain.point

interface PointService {
    fun send(sourceUserId: Long, targetUserId: Long, point: Int)
    fun storeDiaryWriteHistory(userId: Long, diaryId: Long): Long
    fun storeCommentWriteHistory(userId: Long, commentId: Long): Long
}