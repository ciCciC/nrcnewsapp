package com.koray.nrcnewsapp.core.network.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.network.dto.ArticlePageDto
import java.util.function.Consumer

class CategorySelectionModel : ViewModel() {

    // The cashing is being done due to loading the predefined test images.
    private val CASHED_CATEGORIES = HashMap<String, CategoryItemModel>()
    private val category = MutableLiveData<CategoryItemModel>()

    fun setCashCategories(categories: List<CategoryItemModel>){
        categories.forEach{item -> this.CASHED_CATEGORIES[item.topic!!] = item }
    }

    fun getCashedCategories(): HashMap<String, CategoryItemModel> {
        return this.CASHED_CATEGORIES
    }

    fun getCategory(): MutableLiveData<CategoryItemModel> {
        return this.category
    }

    fun setCategory(selectedCategory: CategoryItemModel) {
        this.CASHED_CATEGORIES.values.forEach(Consumer { t -> t.selected = t.topic.equals(selectedCategory.topic) })

        this.category.value = selectedCategory
    }

}