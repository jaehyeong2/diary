package jjfactory.diary.config.security

import jjfactory.diary.common.exception.UnAuthorizedException
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthSupporter {
    companion object {
        private fun getAuthentication(): UserAuthentication {
            val authentication = SecurityContextHolder.getContext().authentication

            if (authentication == null || authentication is AnonymousAuthenticationToken) throw UnAuthorizedException()
            return authentication as UserAuthentication
        }

        fun getLoginUserId(): Long {
            return getAuthentication().getUserId()
        }
    }
}