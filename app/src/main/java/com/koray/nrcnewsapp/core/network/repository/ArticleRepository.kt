package com.koray.nrcnewsapp.core.network.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.koray.nrcnewsapp.core.network.NrcScraperClient
import com.koray.nrcnewsapp.core.network.dto.ArticlePageDto
import com.koray.nrcnewsapp.core.network.dto.ArticleItemDto
import io.reactivex.Observable
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

    fun getAllByCategory(category: String): Observable<List<ArticleItemDto>> {
        return this.nrcScraperClient.getArticlesByCategoryAsync(category)
    }

//    @SuppressLint("CheckResult")
//    fun getAllByCategory(category: String): LiveData<List<ArticleItemDto>> {
//        val data = MutableLiveData<List<ArticleItemDto>>()
//        data.value = emptyList()
//        this.nrcScraperClient
//            .getAllByCategoryAsync(category)
//            .subscribe(
//                { value -> data.value = value },
//                { error -> println("Error: $error") },
//                { println("Completed!") }
//            )
//        return data
//    }

    fun getArticle(articleItemDto: ArticleItemDto, category: String): LiveData<ArticlePageDto> {
        val data = MutableLiveData<ArticlePageDto>()
        data.value = this.nrcScraperClient.getArticle(articleItemDto, category)
        return data
    }

}