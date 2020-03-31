package com.example.typeracer.util

object TextHelper {

    private const val TEXT_MAX_SIZE = 240

    @JvmStatic
    fun emailToDisplayName (email: String?) = email?.substringBefore('@')

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