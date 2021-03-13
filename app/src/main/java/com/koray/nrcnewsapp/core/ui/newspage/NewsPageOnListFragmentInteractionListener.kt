package com.koray.nrcnewsapp.core.ui.newspage

import com.koray.nrcnewsapp.core.domain.NewsPageItemModel

interface NewsPageOnListFragmentInteractionListener {
    fun onListFragmentInteraction(newsPageItem: NewsPageItemModel?)
}