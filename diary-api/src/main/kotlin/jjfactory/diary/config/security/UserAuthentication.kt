package jjfactory.diary.config.security

import io.jsonwebtoken.Claims
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class UserAuthentication(
    private val token: String,
    private val userId: Long,
    private var authenticated: Boolean = false,
    private val authorities: Set<GrantedAuthority> = HashSet(),
) : Authentication {

    constructor(token: String, claims: Claims): this(
        userId = claims.subject.toLong(),
        authenticated = true,
        token = token
    )

    override fun getName(): String {
        return userId.toString()
    }

    override fun getAuthorities(): Set<GrantedAuthority> {
        return authorities
    }

    override fun getCredentials(): String {
        return token
    }

    override fun getDetails(): Long {
        return userId
    }

    override fun getPrincipal(): Long {
        return userId
    }

    override fun isAuthenticated(): Boolean {
        return authenticated
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        authenticated = isAuthenticated
    }

    fun getUserId(): Long {
        return userId
    }
}