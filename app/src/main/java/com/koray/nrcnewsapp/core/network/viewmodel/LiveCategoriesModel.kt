package com.koray.nrcnewsapp.core.network.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.network.dto.CategoryDto
import com.koray.nrcnewsapp.core.network.repository.CategoryRepository
import com.koray.nrcnewsapp.core.network.transformer.CategoryTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LiveCategoriesModel @Inject constructor(
    private var categoryRepository: CategoryRepository
) : ViewModel() {

    @SuppressLint("CheckResult")
    fun getCategories(): LiveData<List<CategoryItemModel>> {
        val mutableLiveData = MutableLiveData<List<CategoryItemModel>>()

        this.categoryRepository.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> mutableLiveData.value = this.transform(response) },
                { error -> println("Error: this is my error!") },
                { println("Nothing returned!") }
            )

        return mutableLiveData
    }

    private fun transform(data: List<CategoryDto>): List<CategoryItemModel> {
        return data.map(CategoryTransformer::toModel)
    }
}