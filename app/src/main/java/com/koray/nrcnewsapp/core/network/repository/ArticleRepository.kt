package com.koray.nrcnewsapp.core.network.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.koray.nrcnewsapp.core.network.NrcScraperClient
import com.koray.nrcnewsapp.core.network.dto.ArticleItemDto
import com.koray.nrcnewsapp.core.network.dto.ArticlePageDto
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository : BaseRepository {

    @Inject
    lateinit var nrcScraperClient: NrcScraperClient

    fun getAll(): LiveData<List<ArticleItemDto>> {
        val data = MutableLiveData<List<ArticleItemDto>>()
        data.value = nrcScraperClient.getAll()
        return data
    }

    fun getAllItemsByCategory(category: String): Observable<List<ArticleItemDto>> {
        return this.nrcScraperClient.getArticlesByCategoryAsync(category)
    }

    fun getArticle(articleItemDto: ArticleItemDto, category: String): Observable<ArticlePageDto> {
        return this.nrcScraperClient.getArticleAsync(articleItemDto, category)
    }

}