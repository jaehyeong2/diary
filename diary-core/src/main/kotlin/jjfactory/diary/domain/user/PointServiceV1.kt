package jjfactory.diary.domain.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class PointServiceV1(
    private val userReader: UserReader
) : PointService {
    override fun send(sourceUserId:Long, targetUserId: Long, point: Int){
        val sender = userReader.getOrThrow(sourceUserId)
        sender.pointDownForTransaction(point)

        val receiver = userReader.getOrThrow(targetUserId)
        receiver.pointUp(point)
    }
}