package com.hencoder.touch.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

class TouchView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
  override fun onTouchEvent(event: MotionEvent): Boolean {
    if (event.actionMasked == MotionEvent.ACTION_UP) {
      performClick()
    }
    return true
  }

  override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
    return super.onInterceptTouchEvent(ev)
  }
  override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    TODO("Not yet implemented")
  }
}