package com.koray.nrcnewsapp.core.network

import com.koray.nrcnewsapp.core.network.dto.ArticlePageDto
import com.koray.nrcnewsapp.core.network.dto.ArticleItemDto
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import javax.inject.Singleton

@Introspected
@Singleton
class NrcScraperClient(
    @Client(ApiStore.API_LOCAL)
    private var httpClient: RxHttpClient
) {

    fun getAll(): List<ArticleItemDto> {
        val req = HttpRequest.GET<Any>("/" + ApiStore.CATEGORY + "/" + ApiStore.TECHNOLOGY)
        return this.httpClient
            .toBlocking()
            .retrieve(req, Argument.listOf(ArticleItemDto::class.java))
    }

    fun getAllByCategory(category: String): List<ArticleItemDto> {
        val req = HttpRequest.GET<Any>("/" + ApiStore.CATEGORY + "/" + category)
        return this.httpClient
            .toBlocking()
            .retrieve(req, Argument.listOf(ArticleItemDto::class.java))
    }

    fun getCategories(): List<String> {
        val req = HttpRequest.GET<Any>("/" + ApiStore.CATEGORY_ITEMS)
        return this.httpClient.toBlocking().retrieve(req, Argument.listOf(String::class.java))
    }

    fun getArticle(articleItemDto: ArticleItemDto, category: String): ArticlePageDto {
        val req = HttpRequest.POST<Any>("/" + ApiStore.CATEGORY + "/" + category + "/" + ApiStore.ARTICLE, articleItemDto)
        return this.httpClient.toBlocking().retrieve(req, ArticlePageDto::class.java)
    }

    fun getFun(): Any {
        val req = HttpRequest.GET<Any>("/")
        return this.httpClient
            .toBlocking()
            .retrieve(req, Argument.of(String::class.java))
    }
}