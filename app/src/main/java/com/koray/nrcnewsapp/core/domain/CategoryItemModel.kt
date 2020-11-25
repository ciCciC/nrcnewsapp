package com.koray.nrcnewsapp.core.domain

import com.koray.nrcnewsapp.core.abstraction.Category
import java.util.*

class CategoryItemModel(
    override var display: String?,
    override var topic: String?,
    var img: Int?,
    var selected: Boolean = false
) : Category {
    override fun equals(other: Any?): Boolean {
        (other as CategoryItemModel)
        return this.hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        return Objects.hash(this.display.hashCode(), this.topic.hashCode())
    }
}