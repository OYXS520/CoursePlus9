package com.hencoder.layoutlayout.view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import java.lang.Integer.max

class TestView(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {
    private val childrenBounds = mutableListOf<Rect>()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        var widthUsed = 0
        var heightUsed = 0
        var lineMaxHeight = 0
        for ((index,child) in children.withIndex()) {
            val layoutParams = child.layoutParams
            measureChildWithMargins(child,widthMeasureSpec,widthUsed,heightMeasureSpec,heightUsed)
            if (index > childrenBounds.size){
                childrenBounds.add(Rect())
            }
            val childBounds = childrenBounds[index]
            childBounds.set(widthUsed,heightUsed,widthUsed + child.measuredWidth, heightUsed + child.measuredHeight)
            widthUsed += child.measuredWidth
            lineMaxHeight = max(lineMaxHeight,child.measuredHeight)
        }
        val selfWidth = widthUsed
        val selfHeight = lineMaxHeight
        setMeasuredDimension(selfWidth,selfHeight)

    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context,attrs)
    }
    override fun onLayout(change: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((index, child) in children.withIndex()) {
            val childBounds = childrenBounds[index]
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom)
        }
    }
}