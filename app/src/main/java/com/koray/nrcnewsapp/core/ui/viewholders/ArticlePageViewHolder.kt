package com.koray.nrcnewsapp.core.ui.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_article_page.view.*

class ArticlePageViewHolder(mView: View): BaseViewHolder(mView) {
    val headerImage: ImageView = mView.articlepage_headerImage
    val topic: TextView = mView.articlepage_topic
    val title: TextView = mView.articlepage_title
    val teaser: TextView = mView.articlepage_teaser
    val layoutContent: LinearLayout = mView.articlepage_content
}