package com.koray.nrcnewsapp.core.network.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koray.nrcnewsapp.core.domain.ArticleItemModel

class ArticleSelectionModel: ViewModel() {

    private val articleItemModel = MutableLiveData<ArticleItemModel>()

    fun getArticleItemModel(): MutableLiveData<ArticleItemModel> {
        return this.articleItemModel
    }

    fun setArticleItemModel(articleItemModel: ArticleItemModel) {
        this.articleItemModel.value = articleItemModel
    }
}