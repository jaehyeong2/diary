package jjfactory.diary.infrastructure.diary

import jjfactory.diary.common.exception.ResourceNotFoundException
import jjfactory.diary.domain.diary.Diary
import jjfactory.diary.domain.diary.DiaryInfo
import jjfactory.diary.domain.diary.DiaryReader
import jjfactory.diary.domain.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DiaryReaderImpl(
    private val diaryRepository: DiaryRepository
) : DiaryReader {
    override fun get(id: Long): Diary? {
        return diaryRepository.findByIdOrNull(id)
    }

    override fun getOrThrow(id: Long): Diary {
        return diaryRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("diary not found")
    }

    override fun getPage(pageable: Pageable): Page<Diary?> {
        return diaryRepository.findPage(pageable) {
            select(
                entity(Diary::class)
            ).from(
                entity(Diary::class)
            )
        }
    }

    override fun getPageInfo(pageable: Pageable, accessLevel: Diary.AccessLevel): Page<DiaryInfo.List?> {
        return diaryRepository.findPage(pageable) {
            selectNew<DiaryInfo.List>(
                path(Diary::id).`as`(expression(Long::class, "id")),
                path(Diary::type).`as`(expression(Diary.Type::class, "type")),
                path(Diary::userId).`as`(expression(Long::class, "userId")),
                path(User::username).`as`(expression(String::class, "username")),
                path(Diary::title).`as`(expression(String::class, "title")),
                path(Diary::createdAt).`as`(expression(LocalDateTime::class, "createdAt")),
                path(Diary::updatedAt).`as`(expression(LocalDateTime::class, "updatedAt")),
            ).from(
                entity(Diary::class),
                join(User::class).on(path(Diary::userId).eq(path(User::id))),
            ).where(
                path(Diary::accessLevel).eq(accessLevel)
            ).orderBy(
                path(Diary::createdAt).desc()
            )
        }
    }
}