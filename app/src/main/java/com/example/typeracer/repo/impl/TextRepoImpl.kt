package com.example.typeracer.repo.impl

import com.example.typeracer.repo.ResponseListener
import com.example.typeracer.repo.rest.RetrofitClient
import com.example.typeracer.repo.TextRepo
import com.example.typeracer.util.TextHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TextRepoImpl : TextRepo {

    override fun getNextText(listener: ResponseListener<String?>) {
        RetrofitClient.apiService.fetchText().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                val randomText = response.body()?.random()
                if (randomText.isNullOrEmpty()) {
                    listener.onFailure()
                } else {
                    val correctText = TextHelper.correctTextSize(randomText)
                    listener.onResponse(correctText)
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                listener.onFailure()
            }
        })
    }

}