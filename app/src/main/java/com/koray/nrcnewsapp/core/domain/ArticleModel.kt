package com.koray.nrcnewsapp.core.domain

import com.koray.nrcnewsapp.core.abstraction.Article
import com.koray.nrcnewsapp.core.abstraction.Section

class ArticleModel(
    override var sectionList: Array<Section>?,
    override var pageLink: String?,
    override var imageLink: String?,
    override var topic: String?,
    override var title: String?,
    override var teaser: String?
) : Article {
}