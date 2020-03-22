package com.koray.nrcnewsapp.core.network.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.network.transformer.CategoryTransformer
import com.koray.nrcnewsapp.core.network.repository.CategoryRepository
import javax.inject.Inject

class LiveCategoriesModel @Inject constructor(
    private var categoryRepository: CategoryRepository
) : ViewModel() {

    fun getCategories(): LiveData<List<CategoryItemModel>> {
        return transform(this.categoryRepository.getAll())
    }

    private fun transform(data: LiveData<List<String>>): MutableLiveData<List<CategoryItemModel>> {
        val transformed = MutableLiveData<List<CategoryItemModel>>()
        transformed.value = data.value?.map(CategoryTransformer::toModel)
        return transformed
    }
}