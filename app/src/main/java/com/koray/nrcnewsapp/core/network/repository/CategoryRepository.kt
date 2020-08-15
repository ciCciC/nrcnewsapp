package com.koray.nrcnewsapp.core.network.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.koray.nrcnewsapp.core.network.NrcScraperClient
import com.koray.nrcnewsapp.core.network.dto.CategoryDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository: BaseRepository {

    @Inject
    lateinit var nrcScraperClient: NrcScraperClient

    fun getAll(): LiveData<List<CategoryDto>> {
        val data = MutableLiveData<List<CategoryDto>>()
        data.value = this.nrcScraperClient.getCategories()
        return data
    }
}