package com.hencoder.text.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.hencoder.text.R
import com.hencoder.text.dp

private val CIRCLE_COLOR = Color.parseColor("#90A4AE")
private val HIGHLIGHT_COLOR = Color.parseColor("#FF4081")
private val RING_WIDTH = 20.dp
private val RADIUS = 150.dp

class TestView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val fontMetrics = Paint.FontMetrics()
    private val textBound = Rect()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 100.dp
        typeface = ResourcesCompat.getFont(context, R.font.font)
        textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //绘制环
        paint.style = Paint.Style.STROKE
        paint.color = CIRCLE_COLOR
        paint.strokeWidth = RING_WIDTH
        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)

        //绘制进度条
        paint.color = HIGHLIGHT_COLOR
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            -90f,
            225f,
            false,
            paint
        )

        //绘制文字
        paint.style = Paint.Style.FILL
        val targetString = "abab"
        paint.getFontMetrics(fontMetrics)
        canvas.drawText(targetString, width / 2f, height / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2, paint)

        //绘制文字2 居顶
//        paint.textSize = 15.dp
//        paint.textAlign = Paint.Align.LEFT
//        paint.getFontMetrics(fontMetrics)
//        canvas.drawText(targetString,0f ,- fontMetrics.top,paint)
//        paint.textSize = 150.dp
//        paint.getFontMetrics(fontMetrics)
//        canvas.drawText(targetString,0f,-fontMetrics.top,paint)

        //居左
        paint.textSize = 15.dp
        paint.getTextBounds(targetString,0,targetString.length,textBound)
        canvas.drawText(targetString,-textBound.left.toFloat(),-textBound.top.toFloat(),paint)
        paint.textSize = 150.dp
        paint.getTextBounds(targetString,0,targetString.length,textBound)
        canvas.drawText(targetString,-textBound.left.toFloat(),-textBound.top.toFloat(),paint)


    }
}