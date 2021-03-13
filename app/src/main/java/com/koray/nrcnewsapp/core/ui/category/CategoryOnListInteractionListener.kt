package com.koray.nrcnewsapp.core.ui.category

import com.koray.nrcnewsapp.core.domain.CategoryItemModel

interface CategoryOnListInteractionListener {
    fun onListFragmentInteraction(category: CategoryItemModel?)
}