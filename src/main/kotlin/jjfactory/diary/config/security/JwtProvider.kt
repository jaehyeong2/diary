package jjfactory.diary.config.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtProvider(
    @Value("\${security.jwt.token.secret-key}")
    private val secretKey: String
) {

    var keyBytes: ByteArray = Base64.getDecoder().decode(secretKey)
    val key: SecretKey = Keys.hmacShaKeyFor(keyBytes)


//    val jwtParser: JwtParser = Jwts.parser()
//        .setSigningKey(key)
//        .build()

    fun create(
        type: String,
        userId: Long,
        expireAt: Date?
    ): String {

        return Jwts.builder()
            .claim("jwtType", type)
            .id(userId.toString())
//            .subject(managerAuthKey.token)
            .setAudience(userId.toString())
            .setIssuedAt(Date())
            .setExpiration(expireAt)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

//    fun extract(token: String): AuthDto.ManagerMain {
//        val claims = parse(token)
//
//        val type = try {
//            JwtTokenType.valueOf(claims["jwtType", String::class.java])
//        } catch (e: IllegalArgumentException) {
//            throw InvalidAccessTokenException()
//        }
//
//        return AuthDto.ManagerMain(
//            type = type,
//            managerId = claims.audience.toLong(),
//            authKeyId = claims.id.toLong(),
//            token = claims.subject
//        )
//    }

//    /** 토큰에서 값 추출하여 반환 */
//    fun extractClientToken(token: String): AuthDto.ClientMain {
//        val claims = parse(token)
//
//        val type = try {
//            JwtTokenType.valueOf(claims["jwtType", String::class.java])
//        } catch (e: IllegalArgumentException) {
//            throw InvalidAccessTokenException()
//        }
//        return AuthDto.ClientMain(
//            type = type,
//            accountId = claims.audience.toLong(),
//            authKeyId = claims.id.toLong(),
//            token = claims.subject
//        )
//    }

//    fun parse(token: String): Claims {
//        return jwtParser.parseClaimsJws(token).body
//    }
}