package com.hencoder.bitmapanddrawable.drawable

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import com.hencoder.bitmapanddrawable.data.Candle
import com.hencoder.bitmapanddrawable.dp

class CandleDrawable(
    val candle: Candle,
    var targetWidth: Float = 20.dp,
    var verticalScale: Float = 1f
) : Drawable() {

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        strokeWidth = targetWidth / 10
        color = if (candle.isGain()) {
            Color.RED
        } else {
            Color.GREEN
        }
    }

    override fun draw(canvas: Canvas) {
        // 绘制蜡烛
        canvas.drawLine(
            targetWidth / 2, 0f, targetWidth / 2, (candle.max - candle.min) * verticalScale, paint
        )
        val middleMax = candle.open.coerceAtLeast(candle.close)
        val middleMin = candle.open.coerceAtMost(candle.close)
        canvas.drawRect(
            0f,
            (candle.max - middleMax) * verticalScale,
            targetWidth,
            (candle.max - middleMin) * verticalScale,
            paint
        )
        // 绘制基本信息
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getAlpha(): Int {
        return paint.alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }
}