package com.koray.nrcnewsapp.core.network.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.koray.nrcnewsapp.core.network.NrcScraperClient
import com.koray.nrcnewsapp.core.network.dto.CategoryDto
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository: BaseRepository {

    @Inject
    lateinit var nrcScraperClient: NrcScraperClient

    fun getAll(): Observable<List<CategoryDto>> {
        return this.nrcScraperClient.getCategoriesAsync()
    }
}