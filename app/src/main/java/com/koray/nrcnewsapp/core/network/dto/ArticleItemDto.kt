package com.koray.nrcnewsapp.core.network.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.koray.nrcnewsapp.core.abstraction.ArticleItem
import com.koray.nrcnewsapp.core.domain.ArticleItemModel

data class ArticleItemDto(
    @JsonProperty("pageLink")
    override var pageLink: String? = "",
    @JsonProperty("imageLink")
    override var imageLink: String? = "",
    @JsonProperty("topic")
    override var topic: String? = "",
    @JsonProperty("title")
    override var title: String? = "",
    @JsonProperty("teaser")
    override var teaser: String? = ""
): ArticleItem {

    override fun hashCode(): Int {
        return (topic + title).hashCode()
    }

    override fun toString(): String {
        return "ArticleItemDto(pageLink=$pageLink, imageLink=$imageLink, topic=$topic, title=$title, teaser=$teaser)"
    }
}