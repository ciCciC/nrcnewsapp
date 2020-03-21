package com.koray.nrcnewsapp.core.domain

class CategoryListItemModel(
    var categoryItemList: List<CategoryItemModel>?,
    override var itemType: NewsPageItemModel.ItemType?
): NewsPageItemModel {
}