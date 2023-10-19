package com.hencoder.viewgroup.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.core.view.children
import kotlin.math.abs

class TestView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    private val overScroller: OverScroller = OverScroller(context)
    private val viewConfiguration: ViewConfiguration = ViewConfiguration.get(context)
    private val velocityTracker = VelocityTracker.obtain()
    private var minVelocity = viewConfiguration.scaledMinimumFlingVelocity
    private var maxVelocity = viewConfiguration.scaledMaximumFlingVelocity
    private var pagingSlop = viewConfiguration.scaledPagingTouchSlop
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var pageStart = 0
        for (child in children) {
            child.layout(pageStart, 0, pageStart + width, height)
            pageStart += width
        }
    }
    private var startX = 0f
    private var startY = 0f
    private var isScroll = false
    private var startScrollX = 0f
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var result = false
        velocityTracker.addMovement(ev)
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                isScroll = false
                startX = ev.x
                startY = ev.y
                startScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                if (abs(ev.x - startX) > pagingSlop){
                    isScroll = true
                    result = true
                }
            }
            else -> {}
        }
        return result

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN){
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
                startScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = (startX - event.x + startScrollX).toInt().coerceAtLeast(0).coerceAtMost(width * (children.count() -1) )
                scrollTo(dx,0)
            }
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000,maxVelocity.toFloat())
                val vx = velocityTracker.xVelocity
                val scrollX = scrollX
                println("shuouyang scrollX = ${scrollX}")

                val currentPageIndex = scrollX / width
                val scrollDistance = if (abs(vx) < minVelocity){
                    if (vx > 0){
                        if (scrollX < currentPageIndex * width - width / 2){
                            -1
                            (currentPageIndex - 1).coerceAtLeast(0) * width - scrollX
                        }else{
                            0
                            currentPageIndex * width - scrollX
                        }
                    }else{
                        if (scrollX > currentPageIndex * width + width / 2){
                            1
                            (currentPageIndex + 1).coerceAtMost(children.count() - 1) * width - scrollX
                        }else{
                            0
                            currentPageIndex * width - scrollX
                        }
                    }
                }else{
                    if (vx > 0) {
                        -1
                        val nextPage = (currentPageIndex - 1).coerceAtLeast(0)
                        (nextPage * width - scrollX) % width
                    } else {
                        1
                        (currentPageIndex + 1).coerceAtMost(children.count() - 1) * width - scrollX
                    }
                }

                overScroller.startScroll(getScrollX(),0, scrollDistance,0)
                postInvalidateOnAnimation()
             }
            else -> {}
        }
        return true
    }

    override fun computeScroll() {
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.currX, overScroller.currY)
            postInvalidateOnAnimation()
        }
    }
}