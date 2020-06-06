package com.koray.nrcnewsapp.core.domain

import android.graphics.drawable.Drawable
import com.koray.nrcnewsapp.core.abstraction.Category

class CategoryItemModel(
    override var name: String?,
    var img: Drawable?
) : Category {
}