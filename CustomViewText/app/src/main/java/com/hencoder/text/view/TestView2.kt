package com.hencoder.text.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.FontMetrics
import android.graphics.Rect
import android.graphics.RectF
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.hencoder.text.R
import com.hencoder.text.dp

private val IMAGE_WIDTH = 150.dp

class TestView2(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    val text =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis sapien turpis, maximus dictum eros at, consectetur ultrices nunc. Maecenas iaculis pellentesque enim, in accumsan justo finibus eu. Vivamus purus mi, luctus eu eleifend et, interdum vitae neque. Nulla mollis vitae libero nec porta. Vestibulum scelerisque dictum blandit. Nam congue ante ut eros sollicitudin viverra. Phasellus rutrum leo a risus auctor, in porttitor mauris luctus. Morbi luctus mi et enim placerat ultrices ut id est. Suspendisse aliquam sem non eros elementum lobortis. Ut facilisis orci a libero venenatis, et elementum nisl sollicitudin. Donec vulputate viverra tempus. Integer euismod id leo in aliquet. Nullam id sodales purus. Nunc quis semper nisi. Nulla sollicitudin scelerisque quam, non euismod odio sollicitudin ac. Sed sed magna non tellus vestibulum finibus.\n" + "\n" + "Aliquam posuere ipsum sed libero tempus convallis. Etiam hendrerit venenatis lacus, a fringilla dolor auctor ut. Nullam pharetra risus accumsan, hendrerit tellus a, rutrum nibh. Pellentesque auctor nisl quis dui dignissim lacinia. Pellentesque quis nulla tempor, rhoncus felis non, placerat velit. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Quisque nunc libero, laoreet nec mauris eget, congue interdum augue. Etiam pretium vitae quam at porta. Mauris diam nisl, sodales eu placerat eu, ultricies in ligula. Nulla at sem eros. Curabitur eleifend metus a diam sagittis, at pellentesque tortor feugiat. Sed ut tellus fermentum eros fermentum finibus. Integer ac lorem consequat, dictum magna sit amet, facilisis erat. Proin scelerisque vestibulum purus, eget maximus magna fringilla non. Quisque hendrerit lorem at nulla efficitur, facilisis interdum libero molestie. Nulla facilisi.\n" + "\n" + "Ut et tincidunt nisi. Integer hendrerit purus purus, et finibus arcu luctus in. Aliquam dapibus tellus id nisi ornare, at condimentum nulla tristique. Nunc eleifend, nulla ac pretium posuere, metus nunc luctus neque, eget sagittis dui metus vel lorem. In eu ligula et libero tincidunt vehicula id non nisl. Pellentesque ac mauris est. Mauris hendrerit sed est eget efficitur. Duis non ullamcorper dolor, id rutrum massa.\n" + "\n" + "Phasellus ultricies volutpat massa. Maecenas lacinia nec elit non dictum. Suspendisse malesuada neque quis nulla lobortis porttitor. Etiam feugiat pretium dapibus. Donec laoreet mi non maximus tempor. Aliquam tortor urna, sodales at scelerisque lobortis, dignissim ac velit. Ut eget ipsum sed nibh volutpat fermentum. Mauris a lacinia dolor. In congue ligula vitae diam viverra condimentum. Nulla in consequat mauris, ut tincidunt dui. Fusce laoreet urna massa, id consequat ex fringilla sed. Nulla vulputate tempor rutrum.\n" + "\n" + "Mauris dui diam, efficitur ac tincidunt et, ultricies sit amet odio. Phasellus porta convallis est sed consequat. Fusce eu porta nibh. Quisque dictum erat ut pellentesque ullamcorper. Aenean eu lorem ante. Nunc odio urna, consectetur non pharetra a, pharetra sit amet velit. Pellentesque a felis ligula. Nunc non dapibus elit. Maecenas aliquet urna ac sapien blandit, id blandit metus vestibulum."
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16.dp
    }
    private val fontMetrics = FontMetrics()
    private val fontBond = Rect()
    private val bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(IMAGE_WIDTH.toInt())
    private val bitmapRect: RectF by lazy {
        RectF(width - IMAGE_WIDTH, 100f.dp, width.toFloat(), 100f.dp + IMAGE_WIDTH)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        val staticLayout = StaticLayout(text,textPaint,width,Layout.Alignment.ALIGN_NORMAL,1f,0f,false)
//        staticLayout.draw(canvas)
        canvas.drawBitmap(bitmap, null, bitmapRect, bitmapPaint)
        textPaint.getFontMetrics(fontMetrics)
        val measureWidth = floatArrayOf(0f)
        var start = 0 //当前行开始时的字符索引
        var count = 0 //当前行需要绘制的字符个数
        var verticalOffset = -fontMetrics.top //垂直偏移
        var maxWidth :Float
        while (start < text.length) {

            if (verticalOffset + fontMetrics.bottom <  bitmapRect.top ||
                verticalOffset + fontMetrics.top > bitmapRect.bottom
                ){
                maxWidth = width.toFloat()
            }else{
                maxWidth = width - IMAGE_WIDTH
            }
            count =
                textPaint.breakText(text, start, text.length, true, maxWidth, measureWidth)
            textPaint.getTextBounds(text, start, start + count, fontBond)
//            if (fontBond.)
            canvas.drawText(text, start, start + count, .0f, verticalOffset, textPaint)
            start += count
            verticalOffset += textPaint.fontSpacing
        }
    }

    fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    }

}