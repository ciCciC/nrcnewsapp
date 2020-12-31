package com.koray.nrcnewsapp.core.domain

class CategoryItemListModel(
    var categoryItemList: List<CategoryItemModel>?,
    override var itemType: NewsPageItemModel.ItemType?
): NewsPageItemModel {
}