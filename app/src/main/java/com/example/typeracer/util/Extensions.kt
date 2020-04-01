package com.example.typeracer.util

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import com.example.typeracer.model.TextPaint
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun <T> List<T>.sublistUntil(index: Int = 0): List<T> {
    return subList(
        0, if (index > size) {
            size
        } else {
            index
        }
    )
}

fun Double.toDecimal2(): Double {
    val decimal2 = DecimalFormat("#.##", DecimalFormatSymbols(Locale.ENGLISH))
    return decimal2.format(this).toDouble()
}

fun SpannableString.paintCharacters(vararg textPaint: TextPaint) {
    this.apply {
        textPaint.forEach {
            val fcs = ForegroundColorSpan(it.color)
            setSpan(fcs, it.start, it.end, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        }
    }
}

infix fun Int.percentFrom(totalCount: Int) = (this * 100) / totalCount

infix fun Long.percentFrom(totalCount: Long) = (this * 100) / totalCount