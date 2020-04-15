package com.koray.nrcnewsapp.core.network.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategorySelectionModel : ViewModel() {

    private val category = MutableLiveData<String>()

    init {
        category.value = ""
    }

    fun isEmpty(): Boolean {
        return category.value!!.isEmpty()
    }

    fun getCategory(): MutableLiveData<String> {
        return this.category
    }

    fun setCategory(category: String) {
        this.category.value = category
    }

}