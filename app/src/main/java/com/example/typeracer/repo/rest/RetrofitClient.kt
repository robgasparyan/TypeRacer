package com.example.typeracer.repo.rest

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val apiService: ApiService = createClient()

    private const val RETRY_COUNT = 3
    private const val RETRY_DELAY_MLS = 3000L

    private const val BASE_URL = "https://api.myjson.com/"

    private fun createClient(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(getClientInterceptor())
            .build()
    }

    /*
     * creating Interceptor for retrying failed network call
     * by specified RETRY_COUNT times.
     * noticed that api.myjson.com public api sometimes fails the ordinal correct requests.
     */
    private fun getClientInterceptor() = Interceptor { chain ->
        // try the request
        var tryCount = 0
        val request = chain.request()
        var response = chain.proceed(request)
        while (!response.isSuccessful && tryCount < RETRY_COUNT) {
            tryCount++
            Thread.sleep(RETRY_DELAY_MLS)
            // retry the request
            response.close()
            response = chain.proceed(request)
        }
        // otherwise return the original response
        response
    }
}