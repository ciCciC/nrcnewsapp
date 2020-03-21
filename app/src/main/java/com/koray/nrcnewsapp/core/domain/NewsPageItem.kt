package com.koray.nrcnewsapp.core.domain

interface NewsPageItemModel {

    var itemType: ItemType?

    enum class ItemType{
        CATEGORY, ARTICLE
    }
}