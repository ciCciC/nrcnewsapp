package com.koray.nrcnewsapp.core.design.util

import android.app.Activity
import androidx.fragment.app.Fragment
import com.koray.nrcnewsapp.BaseApplication

inline fun <reified T> Fragment.inject(): Lazy<T> = lazy {
    (this.activity?.applicationContext as? BaseApplication)?.ctx?.getBean(T::class.java)!!
}

inline fun <reified T> Activity.inject(): Lazy<T> = lazy {
    (this.applicationContext as? BaseApplication)?.ctx?.getBean(T::class.java)!!
}