package com.koray.nrcnewsapp.core.network.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.koray.nrcnewsapp.core.network.NrcScraperClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository: BaseRepository {

    @Inject
    lateinit var nrcScraperClient: NrcScraperClient

    fun getAll(): LiveData<List<String>> {
        val data = MutableLiveData<List<String>>()
        data.value = this.nrcScraperClient.getCategories()
        return data
    }
}