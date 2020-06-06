package com.koray.nrcnewsapp.core.network.transformer

import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.domain.NewsPageItemModel

object CategoryTransformer {

    fun toModel(category: String): CategoryItemModel {
        return CategoryItemModel(
            category,
            null
        )
    }
}