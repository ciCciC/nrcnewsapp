package com.koray.nrcnewsapp.core.network.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koray.nrcnewsapp.core.domain.ArticleItemModel
import com.koray.nrcnewsapp.core.domain.ArticlePageModel
import com.koray.nrcnewsapp.core.network.caching.ArticlePageService
import com.koray.nrcnewsapp.core.network.dto.ArticleItemDto
import com.koray.nrcnewsapp.core.network.transformer.ArticleTransformer
import com.koray.nrcnewsapp.core.network.repository.ArticleRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class LiveArticlesModel @Inject constructor(
    private var articleRepository: ArticleRepository
) : ViewModel() {

    private val articlesByCategoryLiveData = MutableLiveData<List<ArticleItemModel>>()
//    private val articleLiveData = MutableLiveData<ArticlePageModel>()
    private val articlePageCache = ArticlePageService

//    fun getArticleItems(): LiveData<List<ArticleItemModel>> {
//        return transform(articleRepository.getAll());
//    }

    @SuppressLint("CheckResult")
    fun getAllByCategory(category: String): LiveData<List<ArticleItemModel>> {
        articlesByCategoryLiveData.value = emptyList()
        this.articleRepository.getAllByCategory(category)
            .subscribeOn(Schedulers.io())
            .map { data -> transform(data) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { value -> articlesByCategoryLiveData.value = value },
                { error -> println("Error: $error") },
                { println("Completed!") }
            )
        return articlesByCategoryLiveData
    }

//    fun getAllByCategory(category: String): LiveData<List<ArticleItemModel>> {
//        return transform(this.articleRepository.getAllByCategory(category))
//    }

    @SuppressLint("CheckResult")
    fun getArticleTest(
        articleItemModel: ArticleItemModel,
        category: String
    ): LiveData<ArticlePageModel> {
        val mutableLiveData = MutableLiveData<ArticlePageModel>()

        val articleItemDto = ArticleTransformer.toDto(articleItemModel)
        val key = articleItemDto.hashCode().toString()
        val articlePageDto = articlePageCache.get(key)

        if (articlePageDto != null) {
            mutableLiveData.value = ArticleTransformer.toModel(articlePageDto)
            return mutableLiveData
        }

        this.articleRepository.getArticleTest(articleItemDto, category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { value ->
                    mutableLiveData.value = ArticleTransformer.toModel(value)
                    this.articlePageCache.add(key, value)
                },
                { error -> println("Error: $error") },
                { println("Completed!") }
            )

        return mutableLiveData
    }

    fun getArticle(
        articleItemModel: ArticleItemModel,
        category: String
    ): MutableLiveData<ArticlePageModel> {
        val dto = ArticleTransformer.toDto(articleItemModel)

        val mutableLiveData = MutableLiveData<ArticlePageModel>()
        val retrieved =
            this.articleRepository.getArticle(
                dto,
                category
            ).value!! // TODO replace with better null check
        mutableLiveData.value = ArticleTransformer.toModel(retrieved)
        return mutableLiveData
    }

    private fun transform(data: List<ArticleItemDto>): List<ArticleItemModel> {
        return data.map(ArticleTransformer::toModel)
    }

//    private fun transform(data: LiveData<List<ArticleItemDto>>): MutableLiveData<List<ArticleItemModel>> {
//        val transformed = MutableLiveData<List<ArticleItemModel>>()
//        transformed.value = data.value?.map(ArticleTransformer::toModel)
//        return transformed
//    }

//    private fun <I, R> transformCollection(data: LiveData<List<I>>): MutableLiveData<List<R>> {
//        val transformed = MutableLiveData<List<R>>()
//        transformed.value = data.value?.map{i ->  Transformer.toModel(i)}
//        return transformed
//    }
//
//    private fun <I : Transformer<R>, R> transform(aa: LiveData<I>): MutableLiveData<R> {
//        val transformed = MutableLiveData<R>()
//        transformed.value = data.value?.toModel()
//        return transformed
//    }
}