package com.koray.nrcnewsapp.core.design.dummy

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object CategoryDummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<DummyItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<Int, DummyItem> = HashMap()

    init {

        addItem(DummyItem(1, "Games"))
        addItem(DummyItem(2, "Technology"))
        addItem(DummyItem(3, "Physics"))

        addItem(DummyItem(4, "Brussel"))
        addItem(DummyItem(5, "Koray"))
        addItem(DummyItem(6, "Bever"))
    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    public fun createDummyItem(position: Int): DummyItem {
        return DummyItem(position, "Item " + position)
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: Int, var content: String) {
        fun incrementContent(num: Int) = content + num
        override fun toString(): String = content
    }
}
