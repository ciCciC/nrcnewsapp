package com.koray.nrcnewsapp.core.domain

import com.koray.nrcnewsapp.core.abstraction.ArticleItem

class ArticleItemModel(
    override var pageLink: String?,
    override var imageLink: String?,
    override var topic: String?,
    override var title: String?,
    override var teaser: String?
) : ArticleItem {
}