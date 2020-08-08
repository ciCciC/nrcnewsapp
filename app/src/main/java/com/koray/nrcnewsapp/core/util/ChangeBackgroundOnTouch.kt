package com.koray.nrcnewsapp.core.util

import android.content.res.Resources
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import com.koray.nrcnewsapp.R

class ChangeBackgroundOnTouch(val resources: Resources): View.OnTouchListener {
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> v?.setBackgroundColor(Color.LTGRAY)
            MotionEvent.ACTION_UP -> {
                v?.setBackgroundColor(resources.getColor(R.color.colorTransparant, null))
                v?.performClick()
            }
        }
        return true
    }
}