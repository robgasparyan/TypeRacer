package com.example.typeracer.util

import java.text.SimpleDateFormat
import java.util.*

object TextHelper {

    @JvmOverloads
    @JvmStatic
    fun getDateTimeFromTimeStamp(time: Long, dateFormat: String = " HH:mm / dd.MM.yyyy"): String {
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
        return simpleDateFormat.format(Date(time))
    }

}