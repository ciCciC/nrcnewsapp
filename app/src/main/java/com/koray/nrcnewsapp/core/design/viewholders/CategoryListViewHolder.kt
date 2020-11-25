package com.koray.nrcnewsapp.core.design.viewholders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koray.nrcnewsapp.R
import com.koray.nrcnewsapp.core.design.category.CategoryItemRecyclerViewAdapter
import com.koray.nrcnewsapp.core.design.category.CategoryOnListInteractionListener
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.network.viewmodel.CategorySelectionModel

/***
 * Handles the Category list View in NewsPage.
 *
 */
class CategoryListViewHolder(
    mView: View,
    categoriesList: MutableList<CategoryItemModel>,
    listener: CategoryOnListInteractionListener?,
    categoryLiveData: CategorySelectionModel
)
    : BaseViewHolder(mView) {

    init {
        val recyclerView: RecyclerView = mView.findViewById(R.id.category_fragment_list)
        recyclerView.layoutManager =
            LinearLayoutManager(mView.context, RecyclerView.HORIZONTAL, false)

        recyclerView.adapter = CategoryItemRecyclerViewAdapter(
            categoriesList,
            listener,
            categoryLiveData
        )
    }
}