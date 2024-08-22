package jjfactory.diary.domain.point

import jjfactory.diary.domain.point.PointHistory.Type
import jjfactory.diary.domain.point.PointHistory.Type.*
import jjfactory.diary.domain.user.UserReader
import jjfactory.diary.infrastructure.point.PointHistoryRepository
import jjfactory.diary.infrastructure.point.TransactionsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class PointServiceImpl(
    private val userReader: UserReader,
    private val pointHistoryRepository: PointHistoryRepository,
    private val transactionsRepository: TransactionsRepository
) : PointService {
    override fun send(sourceUserId:Long, targetUserId: Long, point: Int){
        val sender = userReader.getOrThrow(sourceUserId)
        val receiver = userReader.getOrThrow(targetUserId)

        val initTransactions = Transactions(
            senderId = sender.id!!,
            receiverId = receiver.id!!,
            amount = point
        )

        val transactions = transactionsRepository.save(initTransactions)

        val initHistory = PointHistory(
            type = SEND,
            userId = sender.id,
            amount = point,
            sourceId = transactions.id!!
        )

        pointHistoryRepository.save(initHistory)
    }

    override fun storeCommentWriteHistory(userId: Long, commentId: Long): Long {
        val user = userReader.getOrThrow(userId)

        val initHistory = PointHistory(
            type = WRITE_COMMENT,
            userId = user.id!!,
            amount = WRITE_COMMENT.amount!!,
            sourceId = commentId
        )

        val history = pointHistoryRepository.save(initHistory)
        return history.id!!
    }

    override fun storeDiaryWriteHistory(userId: Long, diaryId: Long): Long {
        val user = userReader.getOrThrow(userId)

        val initHistory = PointHistory(
            type = WRITE_DIARY,
            userId = user.id!!,
            amount = WRITE_DIARY.amount!!,
            sourceId = diaryId
        )

        val history = pointHistoryRepository.save(initHistory)
        return history.id!!
    }
}