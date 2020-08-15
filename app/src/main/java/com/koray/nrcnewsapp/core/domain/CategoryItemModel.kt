package com.koray.nrcnewsapp.core.domain

import com.koray.nrcnewsapp.core.abstraction.Category

class CategoryItemModel(
    override var display: String?,
    override var topic: String?,
    var img: Int?,
    var selected: Boolean = false
) : Category