package com.koray.nrcnewsapp.core.network.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveToolbarArrow: ViewModel() {

    private val showArrow = MutableLiveData<Int>()

    fun showArrow() {
        showArrow.value = View.VISIBLE
    }

    fun hideArrow() {
        showArrow.value = View.INVISIBLE
    }

    fun getStatus(): MutableLiveData<Int> {
        return showArrow
    }
}