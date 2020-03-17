package com.koray.nrcnewsapp.core.network.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.koray.nrcnewsapp.core.network.repository.NrcRepository
import javax.inject.Inject

class CustomViewModelFactory @Inject constructor(private val nrcRepository: NrcRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(NrcRepository::class.java).newInstance(nrcRepository)
    }
}