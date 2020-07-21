package com.koray.nrcnewsapp.core.util

import android.view.View

object ViewUtil {

    fun hideWhenEmpty(value: CharSequence, view: View) {
        if(value.isEmpty()) view.visibility = View.INVISIBLE
    }
}