package com.koray.nrcnewsapp.core.design.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_article_item.view.*

class ArticleViewHolder(mView: View): BaseViewHolder(mView) {
    val testImage: ImageView = mView.article_item_image
    val mContentView: TextView = mView.article_item_content
}