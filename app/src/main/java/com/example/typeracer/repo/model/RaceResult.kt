package com.example.typeracer.repo.model

import com.google.gson.annotations.SerializedName

class RaceResult {

    @SerializedName("history")
    var history: MutableList<Race> = arrayListOf()

}