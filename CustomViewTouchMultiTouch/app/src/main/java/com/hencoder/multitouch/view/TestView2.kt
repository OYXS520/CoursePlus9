package com.hencoder.multitouch.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.hencoder.multitouch.dp
import com.hencoder.multitouch.getAvatar

class TestView2(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val bitmap = getAvatar(resources,200.dp.toInt())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var offsetX = 0f
    private var offsetY = 0f
    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bitmap,offsetX,offsetY,paint)
    }
    private var downX = 0f
    private var downY = 0f
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val focusX:Float
        val focusY:Float
        var pointerCount = event.pointerCount
        var sumX = 0f
        var sumY = 0f
        var isPointerUp = event.actionMasked == MotionEvent.ACTION_POINTER_UP
        for (i in 0 until pointerCount){
            if (!(isPointerUp && i == event.actionIndex)){
                sumX += event.getX(i)
                sumY += event.getY(i)
            }
        }
        if (isPointerUp){
            pointerCount--
        }
        focusX = sumX / pointerCount
        focusY = sumY / pointerCount

        when(event.actionMasked){
            MotionEvent.ACTION_DOWN ,
            MotionEvent.ACTION_POINTER_DOWN ,
            MotionEvent.ACTION_POINTER_UP -> {
                downX = focusX
                downY = focusY
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }

            MotionEvent.ACTION_MOVE -> {

                offsetX = focusX- downX + originalOffsetX
                offsetY = focusY - downY + originalOffsetY
                invalidate()
            }


            MotionEvent.ACTION_UP -> {

            }

        }
        return true
    }
}