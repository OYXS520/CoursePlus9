package com.hencoder.bitmapanddrawable

import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import com.hencoder.bitmapanddrawable.data.Candle
import com.hencoder.bitmapanddrawable.drawable.CandleDrawable
import kotlinx.android.synthetic.main.activity_main.view1
import kotlinx.android.synthetic.main.activity_main.view2

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    /*val bitmap = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888)

    bitmap.toDrawable(resources)
    val drawable = ColorDrawable()
    drawable.toBitmap()*/
    view1.setImageDrawable(CandleDrawable(Candle(100f,90f,80f,75f), verticalScale = 10f))
    view2.setImageDrawable(CandleDrawable(Candle(100f,80f,90f,60f), verticalScale = 10f))
  }
}