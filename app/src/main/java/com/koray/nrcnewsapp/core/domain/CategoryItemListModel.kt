package com.koray.nrcnewsapp.core.domain

class CategoryItemListModel(
    var categoryItemList: List<CategoryItemModel>? = ArrayList(),
    override var itemType: NewsPageItemModel.ItemType? = NewsPageItemModel.ItemType.CATEGORY
): NewsPageItemModel {
}