package com.koray.nrcnewsapp.core.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveToolbarModel: ViewModel() {

    private val showToolbar = MutableLiveData(View.GONE)

    fun show() {
        showToolbar.value = View.VISIBLE
    }

    fun hide() {
        showToolbar.value = View.INVISIBLE
    }

    fun getState(): LiveData<Int> {
        return this.showToolbar
    }
}