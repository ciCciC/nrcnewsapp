package com.koray.nrcnewsapp.core.network.transformer

import com.koray.nrcnewsapp.core.domain.ArticleItemModel
import com.koray.nrcnewsapp.core.domain.ArticlePageModel
import com.koray.nrcnewsapp.core.domain.NewsPageItemModel
import com.koray.nrcnewsapp.core.network.dto.ArticleItemDto
import com.koray.nrcnewsapp.core.network.dto.ArticlePageDto

object ArticleTransformer {

    fun apply(articleItemDto: ArticleItemDto): ArticleItemModel {
        return ArticleItemModel(
            articleItemDto.pageLink,
            articleItemDto.imageLink,
            articleItemDto.topic,
            articleItemDto.title,
            articleItemDto.teaser,
            NewsPageItemModel.ItemType.ARTICLE
        )
    }

    fun apply(articleDto: ArticlePageDto): ArticlePageModel {
        return ArticlePageModel(
            articleDto.sectionList,
            articleDto.pageLink,
            articleDto.imageLink,
            articleDto.topic,
            articleDto.title,
            articleDto.teaser
        )
    }

    fun apply(articleItemModel: ArticleItemModel): ArticleItemDto {
        return ArticleItemDto(
            articleItemModel.pageLink,
            articleItemModel.imageLink,
            articleItemModel.topic,
            articleItemModel.title,
            articleItemModel.teaser
        )
    }
}