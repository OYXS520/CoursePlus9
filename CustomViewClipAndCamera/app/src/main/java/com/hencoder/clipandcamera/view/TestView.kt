package com.hencoder.clipandcamera.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.hencoder.clipandcamera.R
import com.hencoder.clipandcamera.dp

private val BITMAP_SIZE = 200.dp
private val BITMAP_PADDING = 100.dp

class TestView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(BITMAP_SIZE.toInt())
    private val camera = Camera()

    init {
        camera.rotateX(30f)
//        camera.setLocation(0f,0f,-8f * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas) {
        //上半部分
        canvas.save()
        canvas.translate((BITMAP_PADDING + BITMAP_SIZE / 2f), (BITMAP_PADDING + BITMAP_SIZE / 2f))
        canvas.rotate(-30f)

        canvas.clipRect(
            -BITMAP_SIZE, -BITMAP_SIZE, BITMAP_SIZE, 0f
        )
//        canvas.drawRect(-BITMAP_SIZE/2,
//            -BITMAP_SIZE/2,
//            BITMAP_SIZE/2 ,
//            0f,paint)
        canvas.rotate(30f)
        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE / 2f), -(BITMAP_PADDING + BITMAP_SIZE / 2f))
        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)
        canvas.restore()
        //下半部分
        canvas.save()
        canvas.translate((BITMAP_PADDING + BITMAP_SIZE / 2f), (BITMAP_PADDING + BITMAP_SIZE / 2f))
        canvas.rotate(-30f)
        camera.applyToCanvas(canvas)
        canvas.clipRect(
            -BITMAP_SIZE, 0f, BITMAP_SIZE, BITMAP_SIZE
        )
        canvas.drawRect(
            -BITMAP_SIZE, 0f, BITMAP_SIZE, BITMAP_SIZE, paint
        )
        canvas.rotate(30f)

        canvas.translate(-(BITMAP_PADDING + BITMAP_SIZE / 2f), -(BITMAP_PADDING + BITMAP_SIZE / 2f))
//        canvas.clipRect(BITMAP_PADDING,
//            BITMAP_PADDING + BITMAP_SIZE /2f ,
//            BITMAP_PADDING + BITMAP_SIZE ,
//            BITMAP_PADDING + BITMAP_SIZE)

        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)
        canvas.save()

    }

    private fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    }
}