package jjfactory.diary.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationFilter: AuthenticationFilter,
    private val authExceptionHandlerFilter: AuthExceptionHandlerFilter
) {

    @Value("\${application.Environment}")
    private lateinit var environment: String
    private val TEST_URLS = arrayOf(
//        "/**",
        "/test",
        "/test/**"
    )

    @Bean
    fun filterChainForManager(http: HttpSecurity): SecurityFilterChain {
        if ("dev" == environment || "local" == environment) {
            http.authorizeHttpRequests {
                it.requestMatchers(*TEST_URLS).permitAll()
            }

            http.authorizeHttpRequests { authorizeRequests ->
                authorizeRequests
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // permit options
                    .requestMatchers("/", "/robots.txt").permitAll() // health check && robots.txt && error
                    .requestMatchers("/auth/**", "/regi/**").anonymous()
                    .requestMatchers("/oauth2/*/code", "/oauth2/*/authorization").permitAll()
                    .anyRequest().authenticated()
            }
                .formLogin { it.disable() }
                .logout { it.disable() }
        }

        http.sessionManagement { sessionManagement ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
            .csrf { csrf -> csrf.disable() }
            .cors { it }

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        http.addFilterBefore(authExceptionHandlerFilter, authenticationFilter::class.java)

        return http.build()
    }
}