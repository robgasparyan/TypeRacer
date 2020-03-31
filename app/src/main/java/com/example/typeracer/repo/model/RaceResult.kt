package com.example.typeracer.repo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RaceResult {

    @Expose
    @SerializedName("email")
    var email: String? = null

    @SerializedName("history")
    var history: MutableList<Race> = arrayListOf()

}