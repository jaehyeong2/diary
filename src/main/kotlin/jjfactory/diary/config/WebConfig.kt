package jjfactory.diary.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.Duration

@Configuration
class WebConfig : WebMvcConfigurer {

    @Value("\${application.Environment}")
    private lateinit var environment: String

//    override fun addInterceptors(registry: InterceptorRegistry) {
//        registry.addInterceptor(CustomInterceptor())
//            .order(1)
//            .addPathPatterns("/**")
//            .excludePathPatterns("/css/**", "/*.ico")
//    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()

        if (environment == "develop" || environment == "local") {
            configuration.addAllowedOrigin("http://localhost:3000")
            configuration.addAllowedOrigin("http://localhost:3001")
            configuration.addAllowedOrigin("https://localhost:3000")
            configuration.addAllowedOrigin("https://localhost:3001")
        }

        if (environment == "production") {
        }

        configuration.addAllowedHeader("*")
        configuration.addAllowedMethod("*")
        configuration.setMaxAge(Duration.ofHours(2L));
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}