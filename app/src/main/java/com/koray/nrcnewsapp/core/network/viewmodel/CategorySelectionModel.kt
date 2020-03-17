package com.koray.nrcnewsapp.core.network.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategorySelectionModel : ViewModel() {

    private val category = MutableLiveData<String>()

    fun getCategory(): MutableLiveData<String> {
        return this.category
    }

    fun setCategory(category: String) {
        this.category.value = category
    }

}