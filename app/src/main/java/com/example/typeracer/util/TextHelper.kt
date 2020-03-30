package com.example.typeracer.util

import java.util.concurrent.TimeUnit

object TextHelper {

    private const val TEXT_MAX_SIZE = 240

    fun calculateGrossWpm(correctInputs: Int, spentTimeInMls: Long): Double {
        val spentMinutes = TimeUnit.MILLISECONDS.toSeconds(spentTimeInMls) / 60.0
        return correctInputs / spentMinutes
    }

    fun correctTextSize(source: String): String {
        if (source.length < TEXT_MAX_SIZE) {
            return source
        }
        val indexOfLastSpace = source.substring(0, TEXT_MAX_SIZE).lastIndexOf(" ")
        return source.substring(0, indexOfLastSpace)
            .replace("  ", "")
            .trim(' ', ',')
    }
}