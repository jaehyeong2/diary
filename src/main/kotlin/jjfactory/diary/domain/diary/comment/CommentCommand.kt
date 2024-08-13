package jjfactory.diary.domain.diary.comment

class CommentCommand {
    data class Create(
        val diaryId: Long,
        val content: String
    )

    data class Modify(
        val diaryId: Long,
        val content: String
    )
}