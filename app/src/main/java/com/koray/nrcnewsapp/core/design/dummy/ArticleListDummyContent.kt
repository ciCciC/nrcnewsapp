package com.koray.nrcnewsapp.core.design.dummy

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object ArticleListDummyContent {

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
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i, (i != 1), if(i == 1) ViewType.category else ViewType.articleItem))
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

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: String, val content: String, val viewType: ViewType, val touchable: Boolean) {
        fun isTouchable(): Boolean = touchable
        override fun toString(): String = content
    }

    enum class ViewType{
        category, articleItem
    }
}
