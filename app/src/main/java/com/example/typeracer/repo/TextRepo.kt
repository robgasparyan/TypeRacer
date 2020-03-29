package com.example.typeracer.repo

import com.example.typeracer.repo.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object TextRepo {

    //todo use for fetching new "type race" source
    fun getNextText(listener: ResponseListener<String?>) {
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