package com.koray.nrcnewsapp.core.design.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_article_item.view.*

class ArticleViewHolder(mView: View): BaseViewHolder(mView) {
    val articleItemImg: ImageView = mView.article_item_image
    val topic: TextView = mView.article_item_topic
    val title: TextView = mView.article_item_title
    val teaser: TextView = mView.article_item_teaser
}