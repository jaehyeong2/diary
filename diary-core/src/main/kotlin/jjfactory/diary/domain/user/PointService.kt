package jjfactory.diary.domain.user

interface PointService {
    fun send(sourceUserId: Long, targetUserId: Long, point: Int)
}