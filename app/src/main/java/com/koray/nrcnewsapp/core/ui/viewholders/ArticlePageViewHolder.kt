package com.koray.nrcnewsapp.core.ui.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.koray.nrcnewsapp.R

class ArticlePageViewHolder(mView: View): BaseViewHolder(mView) {
    val headerImage: ImageView = mView.findViewById(R.id.articlepage_headerImage)
    val topic: TextView = mView.findViewById(R.id.articlepage_topic)
    val title: TextView = mView.findViewById(R.id.articlepage_title)
    val teaser: TextView = mView.findViewById(R.id.articlepage_teaser)
    val layoutContent: LinearLayout = mView.findViewById(R.id.articlepage_content)
}