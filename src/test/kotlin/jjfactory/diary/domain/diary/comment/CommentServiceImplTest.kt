package jjfactory.diary.domain.diary.comment

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jjfactory.diary.TestEntityFactory
import jjfactory.diary.common.exception.ResourceNotFoundException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class CommentServiceImplTest {
    @Autowired
    private lateinit var commentService: CommentServiceImpl
    @PersistenceContext
    lateinit var entityManager: EntityManager
    private val testEntityFactory = TestEntityFactory()

    @Transactional
    @Test
    fun `부모 댓글 저징 성공`() {
        val user = testEntityFactory.ofUser()
        entityManager.persist(user)

        val commentId = commentService.create(
            userId = user.id!!,
            command = CommentCommand.Create(
                diaryId = 3L,
                content = "재밌네요~"
            )
        )

        assertThat(commentId).isNotNull
        assertThat(user.point).isEqualTo(1)
    }

    @Transactional
    @Test
    fun `부모 댓글 조회 실패 시 익셉션`() {
        val user = testEntityFactory.ofUser()
        entityManager.persist(user)

        assertThatThrownBy {
            commentService.create(
                userId = user.id!!,
                command = CommentCommand.Create(
                    parentId = 2000L,
                    diaryId = 3L,
                    content = "재밌네요~"
                )
            )
        }.isInstanceOf(ResourceNotFoundException::class.java)
    }

    @Transactional
    @Test
    fun `부모 있을 때 바꾸려고 하면 익셉션`() {
        val user = testEntityFactory.ofUser()
        entityManager.persist(user)

        val parentId = commentService.create(
            userId = user.id!!,
            command = CommentCommand.Create(
                diaryId = 3L,
                content = "재밌네요~",
            )
        )

        val childId = commentService.create(
            userId = user.id!!,
            command = CommentCommand.Create(
                diaryId = 3L,
                content = "재밌네요~",
                parentId = parentId
            )
        )

        assertThatThrownBy {
            commentService.modify(
                id = childId,
                userId = user.id!!,
                command = CommentCommand.Modify(
                    parentId = 2000L,
                    diaryId = 3L,
                    content = "변경~"
                )
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}