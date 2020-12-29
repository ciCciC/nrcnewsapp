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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class LiveArticlesModel @Inject constructor(
    private var articleRepository: ArticleRepository
) : ViewModel() {

    private val articlesByCategoryLiveData = MutableLiveData<List<ArticleItemModel>>()
    private val articlePageCache = ArticlePageService

//    fun getArticleItems(): LiveData<List<ArticleItemModel>> {
//        return transform(articleRepository.getAll());
//    }

    @SuppressLint("CheckResult")
    fun getAllByCategory(category: String): LiveData<List<ArticleItemModel>> {
        articlesByCategoryLiveData.value = emptyList()
        this.articleRepository.getAllItemsByCategory(category)
            .subscribeOn(Schedulers.io())
            .map { data -> transform(data) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> articlesByCategoryLiveData.value = response },
                { error -> println("Error: $error") },
                { println("Nothing returned!") }
            )
        return articlesByCategoryLiveData
    }

    @SuppressLint("CheckResult")
    fun getArticle(
        articleItemModel: ArticleItemModel,
        category: String
    ): LiveData<ArticlePageModel> {
        val mutableLiveData = MutableLiveData<ArticlePageModel>()

        val articleItemDto = transform(articleItemModel)
        val key = articleItemDto.hashCode().toString()
        val articlePageDto = articlePageCache.get(key)

        if (articlePageDto != null) {
            mutableLiveData.value = transform(articlePageDto)
            return mutableLiveData
        }

        this.articleRepository.getArticle(articleItemDto, category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    mutableLiveData.value = transform(response)
                    this.articlePageCache.add(key, response)
                },
                { error -> println("Error: this is my error!, ${error.message}") },
                { println("Nothing returned!") }
            )

        return mutableLiveData
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