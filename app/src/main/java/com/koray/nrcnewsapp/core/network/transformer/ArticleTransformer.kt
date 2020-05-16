package com.koray.nrcnewsapp.core.network.transformer

import com.koray.nrcnewsapp.core.domain.ArticleItemModel
import com.koray.nrcnewsapp.core.domain.ArticlePageModel
import com.koray.nrcnewsapp.core.domain.NewsPageItemModel
import com.koray.nrcnewsapp.core.network.dto.ArticleItemDto
import com.koray.nrcnewsapp.core.network.dto.ArticlePageDto

object ArticleTransformer {

    fun toModel(articleItemDto: ArticleItemDto): ArticleItemModel {
        return ArticleItemModel(
            articleItemDto.pageLink,
            articleItemDto.imageLink,
            articleItemDto.topic,
            articleItemDto.title,
            articleItemDto.teaser,
            NewsPageItemModel.ItemType.ARTICLE
        )
    }

    fun toModel(articleDto: ArticlePageDto): ArticlePageModel {
        return ArticlePageModel(
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

    fun toDto(articleModel: ArticlePageModel): ArticlePageDto {
        return ArticlePageDto(
            articleModel.pageLink,
            articleModel.imageLink,
            articleModel.topic,
            articleModel.title,
            articleModel.teaser,
            articleModel.sectionList
        )
    }
}