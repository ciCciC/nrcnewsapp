package com.koray.nrcnewsapp.core.ui.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.koray.nrcnewsapp.R

class ArticleViewHolder(mView: View): BaseViewHolder(mView) {
    val articleItemImg: ImageView = mView.findViewById(R.id.article_item_image)
    val topic: TextView = mView.findViewById(R.id.article_item_topic)
    val title: TextView = mView.findViewById(R.id.article_item_title)
    val teaser: TextView = mView.findViewById(R.id.article_item_teaser)
}