package com.hencoder.drag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.drag_up_down.collectLayout


class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.drag_up_down)
//    collectLayout.setOnDragListener{v,event ->
//      when (event.action) {
//        DragEvent.ACTION_DROP -> if (v is LinearLayout) {
//          collectLayout.addView(TextView(this).apply {
//            text = event.clipData.getItemAt(0).text
//            textSize = 30f
//          })
//        }
//      }
//      true
//    }

  }
}