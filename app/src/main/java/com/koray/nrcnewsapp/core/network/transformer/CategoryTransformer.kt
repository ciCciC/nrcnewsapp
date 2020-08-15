package com.koray.nrcnewsapp.core.network.transformer

import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.domain.NewsPageItemModel
import com.koray.nrcnewsapp.core.network.dto.CategoryDto

object CategoryTransformer {

    fun toModel(category: CategoryDto): CategoryItemModel {
        return CategoryItemModel(
            category.display,
            category.topic,
            0
        )
    }
}