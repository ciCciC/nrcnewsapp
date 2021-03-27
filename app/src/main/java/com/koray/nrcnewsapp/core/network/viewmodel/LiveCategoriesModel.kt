package com.koray.nrcnewsapp.core.network.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.network.dto.CategoryDto
import com.koray.nrcnewsapp.core.network.helper.ErrorHandler
import com.koray.nrcnewsapp.core.network.repository.CategoryRepository
import com.koray.nrcnewsapp.core.network.transformer.CategoryTransformer
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class LiveCategoriesModel @Inject constructor(
    private var categoryRepository: CategoryRepository
) : ViewModel() {

    private val categoriesLiveData = MutableLiveData<List<CategoryItemModel>>()

    fun getCategories(): LiveData<List<CategoryItemModel>> {
        return this.categoriesLiveData
    }

    @SuppressLint("CheckResult")
    fun requestCategories() {
        this.categoryRepository.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> this.categoriesLiveData.value = this.transform(response) },
                { error ->
                    ErrorHandler.showError(StringBuilder(error.message!!))
                }
            )
    }

    private fun transform(data: List<CategoryDto>): List<CategoryItemModel> {
        return data.map(CategoryTransformer::toModel)
    }
}