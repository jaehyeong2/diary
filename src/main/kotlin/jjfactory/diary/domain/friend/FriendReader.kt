package jjfactory.diary.domain.friend

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FriendReader {
    fun get(id: Long): Friend?
    fun getOrThrow(id: Long): Friend
    fun getPage(pageable: Pageable): Page<Friend?>
}