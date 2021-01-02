package com.koray.nrcnewsapp.core.network.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koray.nrcnewsapp.core.domain.ArticleItemModel
import com.koray.nrcnewsapp.core.domain.ArticlePageModel
import com.koray.nrcnewsapp.core.network.caching.ArticlePageService
import com.koray.nrcnewsapp.core.network.dto.ArticleItemDto
import com.koray.nrcnewsapp.core.network.dto.ArticlePageDto
import com.koray.nrcnewsapp.core.network.transformer.ArticleTransformer
import com.koray.nrcnewsapp.core.network.repository.ArticleRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.StringBuilder
import javax.inject.Inject


class LiveArticleModel @Inject constructor(
    private var articleRepository: ArticleRepository
) : ViewModel() {

    private val articlePageCache = ArticlePageService

    private val articlesByCategoryLiveData: MutableLiveData<List<ArticleItemModel>> by lazy {
        MutableLiveData<List<ArticleItemModel>>().also{
            this.loading.value = true
        }
    }

    private val articlePageLiveData: MutableLiveData<ArticlePageModel> by lazy {
        MutableLiveData<ArticlePageModel>().also{
            this.loading.value = true
        }
    }

    val loading = MutableLiveData<Boolean>()

    fun getArticlesByCategory(): LiveData<List<ArticleItemModel>> {
        return this.articlesByCategoryLiveData
    }

    fun getArticlePage(): LiveData<ArticlePageModel> {
        return articlePageLiveData
    }

    @SuppressLint("CheckResult")
    fun requestArticlesByCategory(category: String) {
        this.articleRepository.getAllItemsByCategory(category)
            .subscribeOn(Schedulers.io())
            .map { data -> transform(data) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    this.articlesByCategoryLiveData.value = response
                    this.loading.value = false
                },
                { error -> println("Error: this is my error!, ${error.message}") }
            )
    }

    @SuppressLint("CheckResult")
    fun requestArticlePage(
        articleItemModel: ArticleItemModel,
        category: String
    ) {
        val articleItemDto = transform(articleItemModel)
        val key = articleItemDto.hashCode().toString()
        val articlePageDto = articlePageCache.get(key)

        if (articlePageDto != null) {
            this.articlePageLiveData.value = transform(articlePageDto)
        }

        this.articleRepository.getArticle(articleItemDto, category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    this.articlePageLiveData.value = transform(response)
                    this.articlePageCache.add(key, response)
                    this.loading.value = false
                },
                { error ->
                    ErrorHandler.ErrorStateObject.showError(StringBuilder(error.message!!))
                }
            )
    }

    private fun transform(data: List<ArticleItemDto>): List<ArticleItemModel> {
        return data.map(ArticleTransformer::apply)
    }

    private fun transform(data: ArticlePageDto): ArticlePageModel {
        return ArticleTransformer.apply(data)
    }

    private fun transform(data: ArticleItemModel): ArticleItemDto {
        return ArticleTransformer.apply(data)
    }
}