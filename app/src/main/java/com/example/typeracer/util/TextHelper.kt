package com.example.typeracer.util

object TextHelper {

    @JvmStatic
    fun emailToDisplayName (email: String?) = email?.substringBefore('@')

    fun correctTextSize(source: String): String {
        if (source.length < Constants.SOURCE_TEXT_MAX_SIZE) {
            return source
        }
        val indexOfLastSpace = source
            .substring(0, Constants.SOURCE_TEXT_MAX_SIZE)
            .lastIndexOf(" ")
        return source.substring(0, indexOfLastSpace)
            .replace("  ", "")
            .trim(' ', ',')
    }
}