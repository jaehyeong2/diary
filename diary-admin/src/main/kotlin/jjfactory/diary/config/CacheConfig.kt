package jjfactory.diary.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cacheManager(): CacheManager {
        val caches = CacheType.values().map {
            CaffeineCache(
                it.cacheName,
                Caffeine.newBuilder()
                    .recordStats()
                    .expireAfterWrite(it.expire, TimeUnit.SECONDS)
                    .maximumSize(it.maxSize)
                    .build()
            )
        }

        val cacheManager = SimpleCacheManager()
        cacheManager.setCaches(caches)
        return cacheManager
    }


    enum class CacheType(val cacheName: String, val expire: Long, val maxSize: Long) {
        DIARY_INFO("diary_detail", 60 * 60, 1000),
        FRIEND_LIST("friend_list", 60 * 60, 1000),
    }
}