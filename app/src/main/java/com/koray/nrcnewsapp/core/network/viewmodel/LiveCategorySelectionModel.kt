package com.koray.nrcnewsapp.core.network.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koray.nrcnewsapp.core.ui.category.CategoryItemRecyclerViewAdapter
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import java.util.function.Consumer

class LiveCategorySelectionModel : ViewModel() {

    private val selectedCategoryItem = MutableLiveData<CategoryItemModel>()
    private val categoryMapsViewHolder =
        HashMap<Int, CategoryItemRecyclerViewAdapter.CategoryItemViewHolder>()

    fun getCategory(): MutableLiveData<CategoryItemModel> {
        return this.selectedCategoryItem
    }

    fun addCategoryWithViewHolder(
        categoryId: Int,
        viewHolder: CategoryItemRecyclerViewAdapter.CategoryItemViewHolder
    ) {
        this.categoryMapsViewHolder[categoryId] = viewHolder
    }

    fun getCategoryMapsViewHolder(): HashMap<Int, CategoryItemRecyclerViewAdapter.CategoryItemViewHolder> {
        return this.categoryMapsViewHolder
    }

    fun setCategory(selectedCategory: CategoryItemModel) {
        this.selectedCategoryItem.value = selectedCategory
    }

}