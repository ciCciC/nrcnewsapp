package com.koray.nrcnewsapp.core.design.util

import android.content.Context
import android.graphics.*
import android.view.View
import android.widget.ImageView
import com.koray.nrcnewsapp.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation


object ImageManager {

    fun loadImage(view: View, image: ImageView, imgUrl: String) {
        Picasso.with(view.context)
            .load(imgUrl)
            .error(R.drawable.noimage)
            .transform(RoundTransformation(20f, 0f))
            .centerCrop()
            .fit()
            .into(image)
    }

    fun loadImage(context: Context, image: ImageView, imgUrl: String) {
        Picasso.with(context)
            .load(imgUrl)
            .error(R.drawable.noimage)
            .centerCrop()
            .fit()
            .into(image)
    }

    private class RoundTransformation(val radius: Float, val margin: Float) : Transformation {

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
            val mDiameter = radius * 2
            val bottom = source.height - margin

            canvas.drawRoundRect(
                RectF(
                    margin, margin, source.width - margin,
                    source.height - margin
                ), radius, radius, paint
            )

//            drawBottomLeftRoundRect(canvas, paint, right, bottom, mDiameter)
//            drawBottomRightRoundRect(canvas, paint, right, bottom, mDiameter)

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
//            canvas.drawRect(RectF(margin + radius, margin, right, bottom), paint)
        }

        fun drawBottomRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float, mDiameter: Float) {
            canvas.drawRoundRect(
                RectF(right - mDiameter, bottom - mDiameter, right, bottom), radius,
                radius, paint
            )
//            canvas.drawRect(RectF(margin, margin, right - radius, bottom), paint)
            canvas.drawRect(
                RectF(right - radius, margin, right, bottom - radius),
                paint
            )
        }

        override fun key(): String {
            return "square()"
        }
    }
}