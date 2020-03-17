package com.koray.nrcnewsapp.core.network.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.koray.nrcnewsapp.core.abstraction.Article
import com.koray.nrcnewsapp.core.abstraction.Section
import com.koray.nrcnewsapp.core.domain.ArticleModel

class ArticleDto(
    @JsonProperty("sectionList")
    override var sectionList: Array<Section>?,
    @JsonProperty("pageLink")
    override var pageLink: String?,
    @JsonProperty("imageLink")
    override var imageLink: String?,
    @JsonProperty("topic")
    override var topic: String?,
    @JsonProperty("title")
    override var title: String?,
    @JsonProperty("teaser")
    override var teaser: String?
) : Article {

}