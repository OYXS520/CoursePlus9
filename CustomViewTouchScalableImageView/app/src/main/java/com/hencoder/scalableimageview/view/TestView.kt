package com.hencoder.scalableimageview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.hencoder.scalableimageview.dp
import com.hencoder.scalableimageview.getAvatar
import kotlin.math.max
import kotlin.math.min

private val IMAGE_SIZE = 300.dp.toInt()
private const val EXTRA_SCALE = 1.5f

class TestView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources, IMAGE_SIZE)
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var bigScala = 0f
    private var smallScala = 0f
    private var big = false
//    private var scaleFraction = 0f
//        set(value) {
//            field = value
//            invalidate()
//        }
    private var currentScala = 0f
        set(value) {
            field = value
            invalidate()
        }
    private val scaleAnimator: ObjectAnimator = ObjectAnimator.ofFloat(this, "currentScala", smallScala, bigScala)
    private val henGestureListener = HenGestureListener()
    private val henFlingRunner = HenFlingRunner()
    private val gestureDetector = GestureDetectorCompat(context, henGestureListener)
    private val scaleGestureListener = HenScaleGestureListener()



    private val scaleGestureDetector = ScaleGestureDetector(context,scaleGestureListener)
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (w - bitmap.width) / 2f
        originalOffsetY = (h - bitmap.height) / 2f
        if (bitmap.width / bitmap.height > w / h) {
            smallScala = w / bitmap.width.toFloat()
            bigScala = h / bitmap.height.toFloat() * EXTRA_SCALE
        } else {
            smallScala = h / bitmap.height.toFloat()
            bigScala = w / bitmap.width.toFloat() * EXTRA_SCALE
        }
        currentScala = smallScala
        scaleAnimator.setFloatValues(smallScala,bigScala)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val scaleFraction = (currentScala - smallScala) / (bigScala - smallScala)
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        canvas.scale(currentScala, currentScala, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val result = scaleGestureDetector.onTouchEvent(event)
        if(!scaleGestureDetector.isInProgress){
            gestureDetector.onTouchEvent(event)
        }
        return result
    }





    private val scroller = OverScroller(context)

    inner class HenGestureListener:GestureDetector.SimpleOnGestureListener(){
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }
        override fun onFling(
            downEvent: MotionEvent?, currentEvent: MotionEvent?, velocityX: Float,//横行速率
            velocityY: Float //纵向速率
        ): Boolean {
            //滑动时，松手，仍然还会惯性滑动，就是fling
            if (big) {
                scroller.fling(
                    offsetX.toInt(),
                    offsetY.toInt(),
                    velocityX.toInt(),
                    velocityX.toInt(),
                    (-(bitmap.width * bigScala - width) / 2).toInt(),
                    ((bitmap.width * bigScala - width) / 2).toInt(),
                    (-(bitmap.height * bigScala - height) / 2).toInt(),
                    ((bitmap.height * bigScala - height) / 2).toInt()
                )
                ViewCompat.postOnAnimation(this@TestView,henFlingRunner)
            }
            return true
        }

        override fun onScroll(
            downEvent: MotionEvent?, currentEvent: MotionEvent?, distanceX: Float, //旧位置 - 新位置
            distanceY: Float //旧位置 - 新位置
        ): Boolean {
            //移动事件的发生
            if (big) {
                offsetX -= distanceX

                offsetY -= distanceY
                fixOffset()
                invalidate()
            }
            return true
        }

        private fun fixOffset() {
            offsetX = min(offsetX, (bitmap.width * bigScala - width) / 2)
            offsetX = max(offsetX, -(bitmap.width * bigScala - width) / 2)
            offsetY = min(offsetY, (bitmap.height * bigScala - height) / 2)
            offsetY = max(offsetY, -(bitmap.height * bigScala - height) / 2)
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            //连续两次点击触发 间隔不超过300ms,也会有防手抖，间隔小于40ms就认为是误触
            big = !big
            if (big) {
                //这里不是让双击放大的位置居中，而是图片从smallScala大小放大到bigScala大小时，让双击的位置尽量居中，但是不超过图片的边界
                offsetX = (e.x - width / 2f) * (1 - bigScala/*/smallScala*/ )
                offsetY = (e.y -height / 2f) * (1 - bigScala/*/smallScala*/ )
                fixOffset()
                scaleAnimator.start()
            } else {
                scaleAnimator.reverse()
            }
            return true
        }

    }
    inner class HenFlingRunner:Runnable{

        override fun run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                ViewCompat.postOnAnimation(this@TestView,this)
            }
        }
    }
    inner class HenScaleGestureListener: ScaleGestureDetector.OnScaleGestureListener {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            offsetX = (detector.focusX - width / 2f) * (1 - bigScala/*/smallScala*/ )
            offsetY = (detector.focusY -height / 2f) * (1 - bigScala/*/smallScala*/ )
            //双指捏合开始
            return true
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            //双指捏合中
            val tempCurrentScala = currentScala * detector.scaleFactor
            return if (tempCurrentScala < smallScala || tempCurrentScala > bigScala){
                false
            }else{
                currentScala *= detector.scaleFactor
                currentScala = currentScala.coerceAtLeast(smallScala).coerceAtMost(bigScala)
                true
            }

        }


        override fun onScaleEnd(detector: ScaleGestureDetector?) {
            //双指捏合结束
        }

    }
}