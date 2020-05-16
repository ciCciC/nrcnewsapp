package com.koray.nrcnewsapp.core.design.util

import android.graphics.*
import android.view.View
import android.widget.ImageView
import com.koray.nrcnewsapp.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation


object ImageManager {

    fun loadImage(view: View, image: ImageView, imgUrl: String) {
        loadImage(view, image, imgUrl, false)
    }

    fun loadImage(view: View, image: ImageView, imgUrl: String, roundImage: Boolean) {

        val picasso = Picasso.with(view.context)

        if(imgUrl.isEmpty()) {
            picasso.load(R.drawable.test_deadstranding)
        } else {
            picasso.load(imgUrl)
        }

            .error(R.drawable.test_deadstranding)
            .transform(if(roundImage) BottomRoundTransformation(80f, 0f) else NoRoundTransformation())
            .centerCrop()
            .fit()
            .into(image)
    }

    private class NoRoundTransformation(): Transformation {
        override fun key(): String {
            return "noRound"
        }

        override fun transform(source: Bitmap?): Bitmap {
            return source!!
        }

    }

    private class BottomRoundTransformation(val radius: Float, val margin: Float) : Transformation {

        override fun transform(source: Bitmap): Bitmap {

            val paint = Paint()
            paint.isAntiAlias = true
            paint.shader = BitmapShader(
                source, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP
            )

            val output = Bitmap.createBitmap(
                source.width, source.height,
                Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(output)

            val right: Float = source.width - margin
            val diameter = radius * 2
            val bottom = source.height - margin

            drawBottomLeftRoundRect(canvas, paint, right, bottom, diameter)
            drawBottomRightRoundRect(canvas, paint, right, bottom, diameter)

            if (source !== output) {
                source.recycle()
            }
            return output
        }

        private fun drawBottomLeftRoundRect(
            canvas: Canvas,
            paint: Paint,
            right: Float,
            bottom: Float,
            mDiameter: Float
        ) {
            canvas.drawRoundRect(
                RectF(margin, bottom - mDiameter, margin + mDiameter, bottom),
                radius, radius, paint
            )
            canvas.drawRect(RectF(margin, margin, margin + mDiameter, bottom - radius), paint)
            canvas.drawRect(RectF(margin + radius, margin, right / 2, bottom), paint)
        }

        fun drawBottomRightRoundRect(
            canvas: Canvas,
            paint: Paint,
            right: Float,
            bottom: Float,
            mDiameter: Float
        ) {
            canvas.drawRoundRect(
                RectF(right - mDiameter, bottom - mDiameter, right, bottom), radius,
                radius, paint
            )
            canvas.drawRect(RectF((right / 2), margin, right - radius, bottom), paint)
            canvas.drawRect(
                RectF((right - radius), margin, right, bottom - radius),
                paint
            )
        }

        override fun key(): String {
            return "bottomRound"
        }
    }
}