package jjfactory.diary.domain.point

import jjfactory.diary.domain.user.UserReader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class PointServiceImpl(
    private val userReader: UserReader
) : PointService {
    override fun send(sourceUserId:Long, targetUserId: Long, point: Int){
        val sender = userReader.getOrThrow(sourceUserId)
//        sender.pointDownForTransaction(point)

        val receiver = userReader.getOrThrow(targetUserId)
//        receiver.pointUp(point)
    }
}