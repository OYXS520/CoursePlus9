package com.hencoder.drawing.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathDashPathEffect
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View

private val RADIUS = 150f.px
private const val OPEN_ANGLE = 120f
private val DASH_WIDTH = 2f.px
private val DASH_LENGTH = 10f.px
private val DASH_COUNT = 20
private val POINTER_LENGTH = 100f.px
private var currentRadio = 6

class TestView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dash = Path()
    private val path = Path()
    private lateinit var pathEffect: PathDashPathEffect

    init {
        paint.strokeWidth = 3f.px
        paint.style = Paint.Style.STROKE
        dash.addRect(0f, 0f, DASH_WIDTH, DASH_LENGTH, Path.Direction.CCW)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        path.addArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            90f + OPEN_ANGLE / 2,
            360f - OPEN_ANGLE
        )
        val pathMeasure = PathMeasure(path, false)
        val dashInterval = (pathMeasure.length - DASH_WIDTH) / DASH_COUNT
        pathEffect = PathDashPathEffect(dash, dashInterval, 0f, PathDashPathEffect.Style.ROTATE)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, paint)
        val cos =
            Math.cos(Math.toRadians(((90f + OPEN_ANGLE / 2 + (360 - OPEN_ANGLE) / 20f * currentRadio).toDouble()))) * POINTER_LENGTH
        val sin =
            Math.sin(Math.toRadians(((90f + OPEN_ANGLE / 2 + (360 - OPEN_ANGLE) / 20f * currentRadio).toDouble())))* POINTER_LENGTH
        canvas.drawLine(width/2f,height/2f,width/2f + cos.toFloat(),height /2f + sin.toFloat(),paint)
        paint.pathEffect = pathEffect
        canvas.drawPath(path, paint)
        paint.pathEffect = null

    }
}