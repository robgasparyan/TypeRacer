package com.example.typeracer.util

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

