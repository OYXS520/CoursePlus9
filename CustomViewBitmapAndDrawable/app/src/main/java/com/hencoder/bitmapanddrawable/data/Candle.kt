package com.hencoder.bitmapanddrawable.data

/**
 * @param open 开盘价格
 * @param close 收盘价格
 * @param max 最高价格
 * @param min 最小价格
 * 收盘价大于开盘价，营利显示红色
 * 收盘价小于等于开盘价，亏损显示绿色
 */
data class Candle(
    val max:Float,
    val open:Float,
    val close:Float,
    val min:Float,
){

    fun isGain():Boolean = close > open
}
