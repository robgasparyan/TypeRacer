package com.example.typeracer.repo

import com.example.typeracer.repo.model.Race
import com.example.typeracer.repo.model.RaceResult
import com.example.typeracer.repo.model.UserRace

interface RaceRepo {

    fun createJsonApiId()

    fun getLasts(count: Int = 50, listener: ResponseListener<List<UserRace>>)

    fun getTops(count: Int = 50, listener: ResponseListener<List<UserRace>>)

    fun getMyself(count: Int = 50, listener: ResponseListener<List<Race>?>)

    fun addRace(race: Race, listener: ResponseListener<Map<String, RaceResult>?>)

}