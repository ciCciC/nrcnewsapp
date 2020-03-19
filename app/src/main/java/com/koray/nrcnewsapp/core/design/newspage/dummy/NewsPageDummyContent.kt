package com.koray.nrcnewsapp.core.design.newspage.dummy

import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object NewsPageDummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<DummyItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items. This should illustrate possibility of having horizontal lists inside a vertical list
        for (i in 1..COUNT) {
            when (i) {
                1 -> addItem(createDummyItem(i, false, ViewType.CATEGORY))
                8 -> addItem(createDummyItem(i, false, ViewType.CATEGORY))
                else -> addItem(createDummyItem(i, true, ViewType.ARTICLE))
            }
        }
    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createDummyItem(position: Int, touchable: Boolean, viewType: ViewType): DummyItem {
        val content = "Dit is een verzamelaarsgame ten top, met scherpe doch cartooneske graphics. Maar op een gegeven moment heb je het wel weer gezien."
        return DummyItem(position.toString(), content, viewType, touchable)
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: String, val content: String, val viewType: ViewType, val touchable: Boolean) {
        fun isTouchable(): Boolean = touchable
        override fun toString(): String = content
    }

    enum class ViewType{
        CATEGORY, ARTICLE
    }
}
