package com.koray.nrcnewsapp.core.network.caching

import com.koray.nrcnewsapp.core.network.dto.ArticlePageDto
import java.time.Duration
import java.util.*


object ArticlePageService: Cache<String, ArticlePageDto> {

    private var cleanupInterval: Timer = Timer("articlepagecachertimer")
    private val DELAY = Duration.ofMinutes(10).toMillis()

    private val cacheMap = HashMap<String, ArticlePageDto>()

    override fun add(key: String, value: ArticlePageDto) {
        this.javaClass.classLoader?.getResourceAsStream("application.yml")
        if(cacheMap.size == 0) {
            println("Caching...")
            cleanupInterval.schedule(object: TimerTask() {
                override fun run() {
                    removeAll()
                }
            }, DELAY)
        } else {
            println("Caching active")
        }
        cacheMap[key] = value
    }

    override fun get(key: String): ArticlePageDto? {
        return if(cacheMap.containsKey(key)) {
            cacheMap[key]
        } else {
            null
        }
    }

    override fun remove(key: String) {
        cacheMap.remove(key)
    }

    override fun removeAll() {
        println("Cache cleared time up!")
        cacheMap.clear()
    }
}