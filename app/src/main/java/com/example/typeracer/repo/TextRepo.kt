package com.example.typeracer.repo

interface TextRepo {

    fun getNextText(listener: ResponseListener<String?>)

}