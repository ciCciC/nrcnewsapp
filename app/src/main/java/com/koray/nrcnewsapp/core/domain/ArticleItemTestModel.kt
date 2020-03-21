package com.koray.nrcnewsapp.core.domain

class ArticleItemTestModel(
    val teaser: String,
    override var itemType: NewsPageItemModel.ItemType?
) : NewsPageItemModel {
}