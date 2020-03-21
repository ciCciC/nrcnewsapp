package com.koray.nrcnewsapp.core.network.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koray.nrcnewsapp.core.domain.ArticleItemModel
import com.koray.nrcnewsapp.core.domain.ArticlePageModel
import com.koray.nrcnewsapp.core.network.dto.ArticleItemDto
import com.koray.nrcnewsapp.core.network.transformer.ArticleTransformer
import com.koray.nrcnewsapp.core.network.repository.ArticleRepository
import javax.inject.Inject


class LiveArticlesModel @Inject constructor(
    private var articleRepository: ArticleRepository
) : ViewModel() {

    fun getArticleItems(): LiveData<List<ArticleItemModel>> {
        return transform(articleRepository.getAll());
    }

    fun getAllByCategory(category: String): LiveData<List<ArticleItemModel>> {
        return transform(this.articleRepository.getAllByCategory(category))
    }

    fun getArticle(articleItemModel: ArticleItemModel): MutableLiveData<ArticlePageModel> {
        val dto = ArticleTransformer.toDto(articleItemModel)

        val mutableLiveData = MutableLiveData<ArticlePageModel>()
        val retrieved =
            this.articleRepository.getArticle(dto).value!! // TODO replace with better null check
        mutableLiveData.value = ArticleTransformer.toModel(retrieved)
        return mutableLiveData
    }

    private fun transform(data: LiveData<List<ArticleItemDto>>): MutableLiveData<List<ArticleItemModel>> {
        val transformed = MutableLiveData<List<ArticleItemModel>>()
        transformed.value = data.value?.map(ArticleTransformer::toModel)
        return transformed
    }

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