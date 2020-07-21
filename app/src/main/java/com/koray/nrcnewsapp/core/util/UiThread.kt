package com.koray.nrcnewsapp.core.util

import android.app.Activity
import android.widget.Toast

class UiThread(var context: Activity) {

    /***
     * msg -> message to show
     * length -> eg Toast.LENGTH_LONG
     */
    fun showFillInMsgUiThread(msg: String, length: Int) {
        context.runOnUiThread {
            initToastMsg(
                msg,
                length
            )
        }
    }

    private fun initToastMsg(msg: String, length: Int) {
        Toast.makeText(context, msg, length).show()
    }
}