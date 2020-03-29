package com.example.typeracer.repo

interface ResponseListener<T> {

    fun onResponse(response: T)

    fun onFailure()

}
