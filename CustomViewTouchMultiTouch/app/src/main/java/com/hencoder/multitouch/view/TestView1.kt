package com.hencoder.multitouch.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.hencoder.multitouch.dp
import com.hencoder.multitouch.getAvatar

class TestView1(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
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
    private var trackPointerId = 0
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                trackPointerId = event.getPointerId(0)
                downX = event.x
                downY = event.y
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                val actionIndex = event.actionIndex
                trackPointerId = event.getPointerId(actionIndex)
                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE -> {

                offsetX = event.getX(event.findPointerIndex(trackPointerId)) - downX + originalOffsetX
                offsetY = event.getY(event.findPointerIndex(trackPointerId)) - downY + originalOffsetY
                invalidate()
            }

            MotionEvent.ACTION_POINTER_UP -> {
                val actionIndex = event.actionIndex
                val findPointerIndex = event.findPointerIndex(actionIndex)
                if (findPointerIndex == trackPointerId){
                    val newIndex:Int = if (actionIndex == event.pointerCount - 1){
                        event.pointerCount - 2
                    }else{
                        event.pointerCount - 1
                    }
                    trackPointerId = event.findPointerIndex(newIndex)
                    downX = event.getX(newIndex)
                    downY = event.getY(newIndex)
                    originalOffsetX = offsetX
                    originalOffsetY = offsetY
                }
            }
            MotionEvent.ACTION_UP -> {

            }

        }
        return true
    }
}