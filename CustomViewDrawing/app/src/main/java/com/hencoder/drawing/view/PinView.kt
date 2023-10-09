package com.hencoder.drawing.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
private val RADIUS = 150f.px
private val ANGLES = arrayListOf<Float>(60f,90f,150f,60f)
private val  COLORS = arrayListOf<Int>(
    Color.parseColor("#fd79a8"),
    Color.parseColor("#d63031"),
    Color.parseColor("#0984e3"),
    Color.parseColor("#fdcb6e"),
    )
class PinView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var startAngle = 0f
        for((index,angle) in ANGLES.withIndex()){
            paint.color = COLORS[index]
            if (index == 0){
                canvas.save()
                val fl = startAngle + angle / 2
                val padding = 50f
                val paddingX = Math.cos(Math.toRadians(fl.toDouble())) * padding
                val paddingY = Math.sin(Math.toRadians(fl.toDouble())) * padding
                canvas.translate(paddingX.toFloat(),paddingY.toFloat())
            }

            canvas.drawArc(width / 2f - RADIUS,
                height / 2f - RADIUS,
                width / 2f + RADIUS,
                height / 2f + RADIUS,startAngle, angle,true,paint)
            startAngle += angle
            if (index == 0){
                canvas.restore()
            }
        }


    }
}