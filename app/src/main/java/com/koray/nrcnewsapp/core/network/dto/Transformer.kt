package com.koray.nrcnewsapp.core.network.dto

import com.koray.nrcnewsapp.core.domain.ArticleItemModel
import com.koray.nrcnewsapp.core.domain.ArticleModel

object Transformer {

    fun toModel(articleItemDto: ArticleItemDto): ArticleItemModel {
        return ArticleItemModel(
            articleItemDto.pageLink,
            articleItemDto.imageLink,
            articleItemDto.topic,
            articleItemDto.title,
            articleItemDto.teaser
        )
    }

    fun toModel(articleDto: ArticleDto): ArticleModel {
        return ArticleModel(
            articleDto.sectionList,
            articleDto.pageLink,
            articleDto.imageLink,
            articleDto.topic,
            articleDto.title,
            articleDto.teaser
        )
    }

    fun toDto(articleItemModel: ArticleItemModel): ArticleItemDto {
        return ArticleItemDto(
            articleItemModel.pageLink,
            articleItemModel.imageLink,
            articleItemModel.topic,
            articleItemModel.title,
            articleItemModel.teaser
        )
    }

    fun toDto(articleModel: ArticleModel): ArticleDto {
        return ArticleDto(
            articleModel.sectionList,
            articleModel.pageLink,
            articleModel.imageLink,
            articleModel.topic,
            articleModel.title,
            articleModel.teaser
        )
    }
}