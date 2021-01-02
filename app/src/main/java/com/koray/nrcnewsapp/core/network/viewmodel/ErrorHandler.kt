package com.koray.nrcnewsapp.core.network.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.StringBuilder

interface ErrorHandler {

    object ErrorStateObject {
        private var liveStateData: MutableLiveData<ErrorMessage> = MutableLiveData(ErrorMessage())

        fun getErrorState(): LiveData<ErrorMessage> {
            return liveStateData
        }

        fun showError(msg: StringBuilder) {
            liveStateData.value = ErrorMessage(true, msg)
        }
    }

    class ErrorMessage(var showMessage: Boolean = false, var message: StringBuilder = StringBuilder("A error occurred"))

}