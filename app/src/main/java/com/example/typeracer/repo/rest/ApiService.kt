package com.example.typeracer.repo.rest

import com.example.typeracer.repo.model.PostResponse
import com.example.typeracer.repo.model.RaceResult
import retrofit2.Call
import retrofit2.http.*

@JvmSuppressWildcards
interface ApiService {

    @GET("bins/{id}}")
    fun getBins(@Path(value = "id", encoded = true) id: String): Call<Map<String, RaceResult>>

    @PUT("bins/{id}}")
    fun putBins(@Path(value = "id", encoded = true) id: String,
                @Body body: Map<String, RaceResult>?): Call<Map<String, RaceResult>>

    @POST("bins")
    fun postBins(@Body body: Map<String, RaceResult>? = mapOf()): Call<PostResponse>

    @GET("https://baconipsum.com/api/?type=meat-and-filler&paras=1&format=json")
    fun fetchText(): Call<List<String>>

}