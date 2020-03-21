package com.koray.nrcnewsapp.core.network.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.koray.nrcnewsapp.core.network.repository.ArticleRepository
import com.koray.nrcnewsapp.core.network.repository.BaseRepository
import com.koray.nrcnewsapp.core.network.repository.CategoryRepository
import javax.inject.Inject
import kotlin.reflect.KClass

class CustomViewModelFactory @Inject constructor(private val baseRepository: BaseRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val repoClass = getRepoClasses().first { repo -> repo.isInstance(baseRepository) }
        return modelClass.getConstructor(repoClass.java).newInstance(baseRepository)
    }

    private fun getRepoClasses(): ArrayList<KClass<out BaseRepository>> {
        return arrayListOf(
            ArticleRepository::class,
            CategoryRepository::class
        )
    }
}