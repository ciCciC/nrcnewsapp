package com.koray.nrcnewsapp.core.network.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koray.nrcnewsapp.core.domain.CategoryItemModel

class CategorySelectionModel : ViewModel() {

    // The cashing is being done due to loading the predefined test images.
    private val CASHED_CATEGORIES = MutableLiveData<List<CategoryItemModel>>()
    private val category = MutableLiveData<String>()

    fun setCashCategories(categories: List<CategoryItemModel>){
        this.CASHED_CATEGORIES.value = categories
    }

    fun getCategory(): MutableLiveData<String> {
        return this.category
    }

    fun setCategory(selectedCategory: String) {
        this.CASHED_CATEGORIES.value?.forEach { cashedCategory ->
            run {
                cashedCategory.selected = cashedCategory.name.equals(selectedCategory)
            }
        }
        this.category.value = selectedCategory
    }

}