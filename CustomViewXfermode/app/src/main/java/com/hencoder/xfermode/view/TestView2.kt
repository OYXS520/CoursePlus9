package com.hencoder.xfermode.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.hencoder.xfermode.R
import com.hencoder.xfermode.px

private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
class TestView2(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bound = RectF(150f.px, 50f.px, 300f.px,200f.px)
    private val circleBitmap = Bitmap.createBitmap(150f.px.toInt(),150f.px.toInt(),Bitmap.Config.ARGB_8888)
    private val squareBitmap = Bitmap.createBitmap(150f.px.toInt(),150f.px.toInt(),Bitmap.Config.ARGB_8888)
    init {
        val canvas = Canvas(circleBitmap)
        paint.color = Color.parseColor("#3498db")
        canvas.drawOval(50f.px,0f.px,150f.px,100f.px,paint)
        canvas.setBitmap(squareBitmap)
        paint.color = Color.parseColor("#e67e22")
        canvas.drawRect(0f.px,50f.px,100f.px,150f.px,paint)
    }
    override fun onDraw(canvas: Canvas) {
        val count = canvas.saveLayer(bound, null)
        canvas.drawBitmap(circleBitmap,150f.px,50f.px,paint)
        paint.xfermode = XFERMODE
        canvas.drawBitmap(squareBitmap,150f.px,50f.px,paint)
        paint.xfermode = null
        canvas.restoreToCount(count)

    }


}