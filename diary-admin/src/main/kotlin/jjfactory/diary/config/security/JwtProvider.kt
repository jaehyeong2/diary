package jjfactory.diary.config.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtProvider(
    @Value("\${security.jwt.token.secret-key}")
    private val secretKey: String
) {
    var keyBytes: ByteArray = Base64.getDecoder().decode(secretKey)
    val key: SecretKey = Keys.hmacShaKeyFor(keyBytes)


    val jwtParser: JwtParser = Jwts.parser()
        .setSigningKey(key)
        .build()

    fun create(
        type: String,
        userId: Long,
        expireAt: Date?
    ): String {
        return Jwts.builder()
            .claim("jwtType", type)
            .id(userId.toString())
            .subject(userId.toString())
            .issuedAt(Date())
            .expiration(expireAt)
            .signWith(key)
            .compact()
    }

    fun extract(token: String): Claims {
        return parse(token)
    }

    fun parse(token: String): Claims {
        return jwtParser.parseClaimsJws(token).body
    }
}