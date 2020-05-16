package com.koray.nrcnewsapp.core.design.category

import com.koray.nrcnewsapp.core.domain.CategoryItemModel

interface CategoryOnListInteractionListener {
    fun onListFragmentInteraction(category: CategoryItemModel?)
}