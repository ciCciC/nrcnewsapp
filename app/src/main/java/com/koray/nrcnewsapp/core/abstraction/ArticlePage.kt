package com.koray.nrcnewsapp.core.abstraction

import com.koray.nrcnewsapp.core.network.dto.SectionDto

interface ArticlePage : BaseArticle {
    var sectionList: Array<SectionDto>?
}