package com.koray.nrcnewsapp.core.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.koray.nrcnewsapp.R

object FragmentAnimation {

    fun noAnimation(fragmentManager: FragmentManager):FragmentTransaction {
        return fragmentManager.beginTransaction()
    }

    fun rightToLeftAnim(fragmentManager: FragmentManager): FragmentTransaction {
        return fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_right,
                R.anim.enter_from_right,
                R.anim.exit_to_right
            )
    }

    fun rightBottomToLeftTop(fragmentManager: FragmentManager): FragmentTransaction {
        return fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.enter_from_rightbottom,
                R.anim.exit_to_right,
                R.anim.enter_from_rightbottom,
                R.anim.exit_to_right
            )
    }

    fun hide(fragment: Fragment, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.animator.fade_in,
                android.R.animator.fade_out
            )
            .hide(fragment)
            .commit()
    }

    fun show(fragment: Fragment, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.animator.fade_in,
                android.R.animator.fade_out
            )
            .show(fragment)
            .commit()
    }

    fun slideAndfade(fragmentManager: FragmentManager):FragmentTransaction {
        return fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                android.R.animator.fade_in,
                android.R.animator.fade_out
            )
    }

    fun slide(fragmentManager: FragmentManager):FragmentTransaction {
        return fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.slide_in,
                R.anim.slide_out
            )
    }
}