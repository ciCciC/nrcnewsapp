package com.koray.nrcnewsapp.core.design.newspage

import com.koray.nrcnewsapp.core.domain.NewsPageItemModel

interface NewsPageOnListFragmentInteractionListener {
    fun onListFragmentInteraction(newsPageItem: NewsPageItemModel?)
}