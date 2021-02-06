package com.koray.nrcnewsapp.core.network.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koray.nrcnewsapp.core.ui.category.CategoryItemRecyclerViewAdapter
import com.koray.nrcnewsapp.core.domain.CategoryItemModel

class LiveCategorySelectionModel : ViewModel() {

    private val selectedCategoryItem = MutableLiveData<CategoryItemModel>()
    private val categoryViewHolderMap =
        HashMap<Int, CategoryItemRecyclerViewAdapter.CategoryItemViewHolder>()

    fun getCategory(): MutableLiveData<CategoryItemModel> {
        return this.selectedCategoryItem
    }

    fun addCategoryWithViewHolder(
        categoryId: Int,
        viewHolder: CategoryItemRecyclerViewAdapter.CategoryItemViewHolder
    ) {
        this.categoryViewHolderMap[categoryId] = viewHolder
    }

    fun getCategoryViewHolderMap(): HashMap<Int, CategoryItemRecyclerViewAdapter.CategoryItemViewHolder> {
        return this.categoryViewHolderMap
    }

    fun setCategory(selectedCategory: CategoryItemModel) {
        this.selectedCategoryItem.value = selectedCategory
    }

}