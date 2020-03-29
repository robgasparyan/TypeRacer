package com.example.typeracer.repo.model

import com.google.gson.annotations.SerializedName

data class Race(

    @SerializedName("wpm")
    val wpm: Double = 0.0,

    @SerializedName("date")
    val time: Long? = null

)