package com.koray.nrcnewsapp.core.network.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import java.lang.StringBuilder

object ErrorHandler {

    private var liveStateData: MutableLiveData<ErrorMessage> = MutableLiveData(ErrorMessage())

    fun getErrorState(): LiveData<ErrorMessage> {
        return liveStateData
    }

    fun showError(msg: StringBuilder) {
        liveStateData.value = ErrorMessage(true, msg)
    }

    fun mapper(httpCode: Int) {
        when(httpCode){
//            400 -> throw HttpStatusException(HttpStatus.BAD_REQUEST, "Invalid request.")
//            401 -> throw HttpStatusException(HttpStatus.UNAUTHORIZED, "Incorrect credentials.")
            404 -> throw HttpStatusException(HttpStatus.BAD_REQUEST, "Resource is not available.")
            500 or 503 -> throw HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service is not available.")
        }
    }

    class ErrorMessage(var showMessage: Boolean = false, var message: StringBuilder = StringBuilder("A error occurred"))

}