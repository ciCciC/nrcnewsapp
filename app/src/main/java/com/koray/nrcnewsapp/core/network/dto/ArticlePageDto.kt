package com.koray.nrcnewsapp.core.network.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.koray.nrcnewsapp.core.abstraction.ArticlePage

class ArticlePageDto(
    @JsonProperty("pageLink")
    override var pageLink: String?,

    @JsonProperty("imageLink")
    override var imageLink: String?,

    @JsonProperty("topic")
    override var topic: String?,

    @JsonProperty("title")
    override var title: String?,

    @JsonProperty("teaser")
    override var teaser: String?,

    @JsonProperty("sectionList")
    override var sectionList: Array<SectionDto>?

) : ArticlePage {
    override fun toString(): String {
        return "ArticlePageDto(pageLink=$pageLink, imageLink=$imageLink, topic=$topic, title=$title, teaser=$teaser, sectionList=${sectionList?.contentToString()})"
    }
}