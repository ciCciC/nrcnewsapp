package com.koray.nrcnewsapp.core.network.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.koray.nrcnewsapp.core.network.NrcScraperClient
import com.koray.nrcnewsapp.core.network.dto.ArticleDto
import com.koray.nrcnewsapp.core.network.dto.ArticleItemDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NrcRepository {

    @Inject
    lateinit var nrcScraperClient: NrcScraperClient

    fun getAll(): LiveData<List<ArticleItemDto>> {
        val data = MutableLiveData<List<ArticleItemDto>>()
        data.value = nrcScraperClient.getAll()
        return data
    }

    fun getCategories(): LiveData<List<String>> {
        val data = MutableLiveData<List<String>>()
        data.value = this.nrcScraperClient.getCategories()
        return data
    }

    fun getAllByCategory(category: String): LiveData<List<ArticleItemDto>> {
        val data = MutableLiveData<List<ArticleItemDto>>()
        data.value = this.nrcScraperClient.getAllByCategory(category)
        return data
    }

    fun getArticle(articleItemDto: ArticleItemDto): LiveData<ArticleDto> {
        val data = MutableLiveData<ArticleDto>()
        data.value = this.nrcScraperClient.getArticle(articleItemDto)
        return data
    }

}