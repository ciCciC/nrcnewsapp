package com.koray.nrcnewsapp.core.domain

import com.koray.nrcnewsapp.core.abstraction.ArticlePage
import com.koray.nrcnewsapp.core.network.dto.SectionDto

class ArticlePageModel(
    override var sectionList: Array<SectionDto>?,
    override var pageLink: String?,
    override var imageLink: String?,
    override var topic: String?,
    override var title: String?,
    override var teaser: String?
) : ArticlePage