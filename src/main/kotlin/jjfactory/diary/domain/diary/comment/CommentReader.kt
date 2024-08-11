package jjfactory.diary.domain.diary.comment

interface CommentReader {
    fun get(id: Long): Comment?
    fun getOrThrow(id: Long): Comment
}