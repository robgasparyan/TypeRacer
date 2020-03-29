package com.example.typeracer.repo.impl

import com.example.typeracer.repo.ResponseListener
import com.example.typeracer.repo.rest.RetrofitClient
import com.example.typeracer.repo.TextRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TextRepoImpl: TextRepo {

    override fun getNextText(listener: ResponseListener<String?>) {
        RetrofitClient.apiService.fetchText().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                listener.onResponse(response.body()?.random())
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                listener.onFailure()
            }
        })
    }

}