package com.hencoder.materialedittext

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

private val TEXT_SIZE = 12.dp
private val TEXT_MARGIN = 8.dp
private val HORIZONTAL_OFFSET = 8.dp
private val VERTICAL_OFFSET = 23.dp
private val EXTRA_VERTICAL_OFFSET = 16.dp
class PracticeEditText(context: Context,attrs:AttributeSet?): AppCompatEditText(context,attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var floatingLabelShown = false
    private val animator by lazy { ObjectAnimator.ofFloat(this@PracticeEditText,"floatingLabelFraction",0f,1f) }
    var floatingLabelFraction = 0f
        set(value) {
            field = value
            invalidate()
        }
    var useFloatingLabel = false
        set(value) {
            if (field != value){
                field = value
                if (field){
                    setPadding(paddingLeft,(paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(),paddingRight,paddingRight)
                }else{
                    setPadding(paddingLeft,(paddingTop - TEXT_SIZE - TEXT_MARGIN).toInt(),paddingRight,paddingRight)
                }
            }
        }
    init {
        paint.textSize = TEXT_SIZE
        if (useFloatingLabel){
            setPadding(paddingLeft,(paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(),paddingRight,paddingRight)
        }
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (text.isNullOrEmpty() && floatingLabelShown){
            floatingLabelShown = false
            animator.reverse()
        }else if(! text.isNullOrEmpty() && !floatingLabelShown ){
            floatingLabelShown = true
            animator.start()
        }
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.alpha = (floatingLabelFraction * 0xFF).toInt()
        val currentVerticalValue = VERTICAL_OFFSET + (1 - floatingLabelFraction) * EXTRA_VERTICAL_OFFSET
        canvas.drawText(hint.toString() ,HORIZONTAL_OFFSET,currentVerticalValue,paint)
    }
}