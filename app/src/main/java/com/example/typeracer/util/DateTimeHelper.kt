package com.example.typeracer.util

import android.content.Context
import com.example.typeracer.R
import java.util.*
import java.util.concurrent.TimeUnit

object DateTimeHelper {

    fun toUserFriendlyDate(context: Context, data: Long): String {
        try {
            val past = Date(data)
            val now = Date()
            val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
            val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
            val days = TimeUnit.MILLISECONDS.toDays(now.time - past.time)

            return when {
                seconds < 0 -> context.getString(R.string.invalid_date)
                seconds in 1..59 -> String.format(context.getString(R.string.seconds_ago), seconds)
                minutes < 60 -> String.format(context.getString(R.string.minutes_ago), minutes)
                hours < 24 -> String.format(context.getString(R.string.hours_ago), hours)
                else -> String.format(context.getString(R.string.days_ago), days)
            }
        } catch (j: Exception) {
            j.printStackTrace()
        }
        return context.getString(R.string.invalid_date)
    }

}