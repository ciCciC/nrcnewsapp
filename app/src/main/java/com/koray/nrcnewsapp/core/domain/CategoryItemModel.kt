package com.koray.nrcnewsapp.core.domain

import com.koray.nrcnewsapp.core.abstraction.Category

class CategoryItemModel(
    override var name: String?,
    var img: Int?
) : Category {
}