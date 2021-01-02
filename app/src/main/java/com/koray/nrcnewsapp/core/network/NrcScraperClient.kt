package com.koray.nrcnewsapp.core.network

import com.koray.nrcnewsapp.core.network.dto.ArticlePageDto
import com.koray.nrcnewsapp.core.network.dto.ArticleItemDto
import com.koray.nrcnewsapp.core.network.dto.CategoryDto
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.exceptions.HttpStatusException
import io.reactivex.rxjava3.core.Observable
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

    fun getArticlesByCategoryAsync(category: String): Observable<List<ArticleItemDto>> {
        val req = HttpRequest.GET<Any>("/" + ApiStore.CATEGORY + "/" + category)
        val argumentType = Argument.listOf(ArticleItemDto::class.java)
        return Observable.fromCallable { this.request(req, argumentType) }
    }

    fun getArticleAsync(articleItemDto: ArticleItemDto, category: String) : Observable<ArticlePageDto>  {
        val req = HttpRequest.POST<Any>("/" + ApiStore.CATEGORY + "/" + category + "/" + ApiStore.ARTICLE, articleItemDto)
        val argumentType = Argument.of(ArticlePageDto::class.java)
        return Observable.fromCallable {
            val response = this.httpClient
                .toBlocking()
                .exchange(req, argumentType)

            if(response.status.code == 200) {
                throw HttpStatusException(HttpStatus.BAD_REQUEST, "awesome it works!")
            }

            response.body()
        }
    }

    fun getCategoriesAsync(): Observable<List<CategoryDto>> {
        val req = HttpRequest.GET<Any>("/" + ApiStore.CATEGORY_ITEMS)
        val argumentType = Argument.listOf(CategoryDto::class.java)

        return Observable.fromCallable {
            val response = this.request(req, argumentType)
            response
        }
    }

    private fun <T> request(req: MutableHttpRequest<Any>, argument: Argument<T>): T {
        return this.httpClient
            .toBlocking()
            .retrieve(req, argument)
    }

}