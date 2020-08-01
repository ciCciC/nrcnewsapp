package com.koray.nrcnewsapp.core.util

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.koray.nrcnewsapp.R

object AnimationEffect {

    fun shakeImage(
        view: View,
        context: Context?,
        imageId: Int
    ) {
        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
        val image = view.findViewById<ImageView>(imageId)
        image.startAnimation(shake)
    }

    fun shake(view: View, duration: Long) {
        ObjectAnimator
            .ofFloat(view, "translationX", 0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)
            .setDuration(duration)
            .start()
    }

    fun shakeImage(view: View, context: Context?) {
        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
        view.startAnimation(shake)
    }

    fun shakeImage(
        view: View,
        context: Context?,
        imageId: Int,
        duration: Int
    ) {
        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
        shake.duration = duration * 60.toLong()
        val image = view.findViewById<ImageView>(imageId)
        image.startAnimation(shake)
    }

    fun moveObject(
        view: View?,
        propertyname: String?,
        from: Float,
        to: Float,
        duration: Int
    ) {
        // e.g.        AnimationEffect.moveObject(view, "translationX", 1000f, 0f, 1000);
        val objectAnimator = ObjectAnimator.ofFloat(view, propertyname, from, to)
        objectAnimator.duration = duration.toLong()
        objectAnimator.start()
    }

    fun moveObject(
        propertyname: String,
        from: Float,
        to: Float,
        duration: Int,
        views: List<View>
    ) {
        for (view in views) {
            initMoveObjectAnimator(view, propertyname, from, to, duration)
        }
    }

    private fun initMoveObjectAnimator(
        view: View,
        propertyname: String,
        from: Float,
        to: Float,
        duration: Int
    ) {
        val objectAnimator =
            ObjectAnimator.ofFloat(view, View.ALPHA, 0.2f, 0.1f)
        objectAnimator.duration = duration.toLong()
        objectAnimator.repeatCount = 3
        objectAnimator.start()
    }

    fun smallToBigObjects(views: List<View>) {
        for (view in views) {
            smallToBigObjectAnimator(view)
        }
    }

    fun smallToBigObject(view: View) {
        smallToBigObjectAnimator(view)
    }

    private fun smallToBigObjectAnimator(view: View) {
        val k0 = Keyframe.ofFloat(0f, 1f)
        val k1 = Keyframe.ofFloat(0.275f, 4.1f)
        val k2 = Keyframe.ofFloat(0.69f, 0.2f)
        val k3 = Keyframe.ofFloat(1f, 1f)
        val scaleX = PropertyValuesHolder.ofKeyframe("scaleX", k0, k1, k2, k3)
        val scaleY = PropertyValuesHolder.ofKeyframe("scaleY", k0, k1, k2, k3)
        val animator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY)
        animator.duration = 1000
        animator.start()
    }

    fun fadeIn(view: View, duration: Long = 2000) {
        val alphaAnimation =
            ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f)
        alphaAnimation.duration = duration
        alphaAnimation.start()
    }

    fun fadeIn(views: List<View>) {
        for (view in views) {
            fadeIn(view)
        }
    }

    fun rotate(repeatCount: Int = 0, duration: Long = 2 * 1000): RotateAnimation{
        val r = RotateAnimation(0.0f, -10.0f * 360.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f)
        r.duration = duration
        r.repeatCount = repeatCount
        return r
    }
}