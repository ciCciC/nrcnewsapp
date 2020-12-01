package com.koray.nrcnewsapp.core.network

import com.koray.nrcnewsapp.core.network.caching.ArticlePageService
import com.koray.nrcnewsapp.core.network.dto.ArticlePageDto
import com.koray.nrcnewsapp.core.network.dto.ArticleItemDto
import com.koray.nrcnewsapp.core.network.dto.CategoryDto
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.reactivex.Observable
import javax.inject.Singleton

@Introspected
@Singleton
class NrcScraperClient(
    @Client(ApiStore.API_LOCAL)
    private var httpClient: RxHttpClient
) {

    val articlePageCache = ArticlePageService

    fun getAll(): List<ArticleItemDto> {
        val req = HttpRequest.GET<Any>("/" + ApiStore.CATEGORY + "/" + ApiStore.TECHNOLOGY)
        return this.httpClient
            .toBlocking()
            .retrieve(req, Argument.listOf(ArticleItemDto::class.java))
    }

    fun getArticlesByCategoryAsync(category: String): Observable<List<ArticleItemDto>> {
        val req = HttpRequest.GET<Any>("/" + ApiStore.CATEGORY + "/" + category)
        val argumentType = Argument.listOf(ArticleItemDto::class.java)
        return Observable.fromCallable { this.request(req, argumentType) }
//        return Observable.just(this.getAllByCategory(category))
    }

    private fun <T> request(req: MutableHttpRequest<Any>, argument: Argument<T>): T {
        return this.httpClient
            .toBlocking()
            .retrieve(req, argument)
    }

    fun getCategories(): List<CategoryDto> {
        val req = HttpRequest.GET<Any>("/" + ApiStore.CATEGORY_ITEMS)
        return this.httpClient.toBlocking().retrieve(req, Argument.listOf(CategoryDto::class.java))
    }

    fun getArticle(articleItemDto: ArticleItemDto, category: String): ArticlePageDto {
        val key = articleItemDto.hashCode().toString()
        val articlePageDto = articlePageCache.get(key)

        if(articlePageDto != null) {
            return articlePageDto
        }

        val req = HttpRequest.POST<Any>("/" + ApiStore.CATEGORY + "/" + category + "/" + ApiStore.ARTICLE, articleItemDto)
        val fetched = this.httpClient.toBlocking().retrieve(req, Argument.of(ArticlePageDto::class.java))
        articlePageCache.add(key, fetched)
        return fetched
    }

    fun getFun(): Any {
        val req = HttpRequest.GET<Any>("/")
        return this.httpClient
            .toBlocking()
            .retrieve(req, Argument.of(String::class.java))
    }
}