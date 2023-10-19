package com.hencoder.drag.test

import android.content.ClipData
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.children
import java.util.ArrayList

class DragGridView(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private var childSize: Float = 0f
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val predictWidth = resolveSize(width, widthMeasureSpec)
        val predictHeight = resolveSize(width, heightMeasureSpec)
        childSize = if (predictWidth > predictHeight) {
            predictHeight / 3f
        } else {
            predictWidth / 3f
        }
        Log.d("shuouyan", "onMeasure: childSize:${childSize}")
        for (child in children) {
            child.measure(
                MeasureSpec.makeMeasureSpec(childSize.toInt(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(childSize.toInt(), MeasureSpec.EXACTLY),
            )
        }
        setMeasuredDimension(predictWidth, predictHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((index, child) in children.withIndex()) {
            val column = (index % 3)
            val row = index / 3
            val size = childSize.toInt()
            child.layout(
                column * size, row * size, column * size + size, row * size + size
            )
        }
    }

    private var draggedView: View? = null
    private var dragStarter = OnLongClickListener { v ->
        draggedView = v //长按开始的View
        if (v is TextView){
            val dragData = ClipData.newPlainText("icon", v.text)
//        ViewCompat.startDragAndDrop(v, dragData, DragShadowBuilder(v), v, 0)
            v.startDrag(dragData, DragShadowBuilder(v), v, 0)
        }

        false
    }
    private var dragListener = OnDragListener { v, event ->
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED ->  //动作拖拽开始
                //event.localState 拖拽事件所处的地方
                //v 被拖拽的View
                if (event.localState === v) { //拖拽开始时将拖拽
                    v.visibility = View.INVISIBLE
                }

            DragEvent.ACTION_DRAG_ENTERED -> //动作拖动进入某个View
                {
                    val localView = event.localState
                    if (localView !== v) {
                        sort(v)
                    }
                }
            DragEvent.ACTION_DRAG_EXITED -> //动作拖拽退出某个View
                {}
            DragEvent.ACTION_DRAG_ENDED -> //动作拖拽结束
                if (event.localState === v) {
                    v.visibility = View.VISIBLE
                }
        }
        true
    }
    data class RecordView(var preIndex:Int,val view: View,var dx:Float,var dy:Float)
    private var orderedChildren: MutableList<RecordView> = ArrayList()

    override fun onFinishInflate() {
        super.onFinishInflate()
        for ((index,child) in children.withIndex()) {
            orderedChildren.add(RecordView(index,child,0f,0f))
            child.setOnLongClickListener(dragStarter)
            child.setOnDragListener(dragListener)
        }
    }
    private fun sort(targetView: View){
        var draggedIndex = -1 //被拖拽的View的索引
        var targetIndex = -1 //拖拽到的地方的View的索引
        for ((index, child) in  orderedChildren.withIndex()){
            if (targetView === child.view){
                targetIndex = index
            }else if(draggedView == child.view){
                draggedIndex = index
            }
        }
//        if (targetIndex != -1){
            val remove = orderedChildren.removeAt(draggedIndex)
            orderedChildren.add(targetIndex, remove)
            for ((index, child) in orderedChildren.withIndex()) {
                val targetColumn = (index % 3)
                val targetRow = index / 3
                val preColumn = child.preIndex % 3
                val preRow = child.preIndex / 3
                val size = childSize
                child.dx += targetColumn * size - preColumn * size
                child.dy += targetRow * size - preRow * size
                child.view.animate()
                    .translationX(child.dx)
                    .translationY(child.dy)
                    .setDuration(150)
                child.preIndex = index
            }
//        }
    }

}
