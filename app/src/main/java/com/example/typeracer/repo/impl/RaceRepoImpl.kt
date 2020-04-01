package com.example.typeracer.repo.impl

import com.example.typeracer.repo.RaceRepo
import com.example.typeracer.repo.ResponseListener
import com.example.typeracer.repo.rest.RetrofitClient
import com.example.typeracer.repo.model.PostResponse
import com.example.typeracer.repo.model.Race
import com.example.typeracer.repo.model.RaceResult
import com.example.typeracer.repo.model.UserRace
import com.example.typeracer.repo.prefs.Preferences
import com.example.typeracer.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RaceRepoImpl : RaceRepo {

    private var jsonApiId: String? = Preferences.getString(Constants.MY_JSON_API_ID, "6x7qw")

    override fun createJsonApiId() {
        if (jsonApiId.isNullOrEmpty()) {
            createJsonApiId {
                jsonApiId = it
                Preferences.putString(Constants.MY_JSON_API_ID, jsonApiId)
            }
        }
    }

    override fun getLasts(count: Int, listener: ResponseListener<List<UserRace>>) {
        getAllUserRace(object : ResponseListener<List<UserRace>> {
            override fun onResponse(response: List<UserRace>) {
                val topRace = response.sortedBy { it.lastPlayedTime }
                    .reversed().sublistUntil(index = count)
                listener.onResponse(topRace)
            }

            override fun onFailure() {
                listener.onFailure()
            }
        })
    }

    override fun getTops(count: Int, listener: ResponseListener<List<UserRace>>) {
        getAllUserRace(object : ResponseListener<List<UserRace>> {
            override fun onResponse(response: List<UserRace>) {
                val topRace = response.sortedBy { it.averageWpm }
                    .reversed().sublistUntil(index = count)
                listener.onResponse(topRace)
            }

            override fun onFailure() {
                listener.onFailure()
            }
        })
    }

    override fun getMyself(count: Int, listener: ResponseListener<List<Race>?>) {
        getHistory(object : ResponseListener<Map<String, RaceResult>?> {
            override fun onResponse(response: Map<String, RaceResult>?) {
                val historyList = response?.get(FirebaseAuthManager.getCurrentUser()?.uid)?.history
                val myRace =
                    historyList?.sortedBy { it.time }?.reversed()?.sublistUntil(index = count)
                listener.onResponse(myRace)
            }

            override fun onFailure() {
                listener.onFailure()
            }
        })
    }

    override fun addRace(race: Race, listener: ResponseListener<Map<String, RaceResult>?>) {
        getHistory(object : ResponseListener<Map<String, RaceResult>?> {
            override fun onResponse(response: Map<String, RaceResult>?) {
                val mutableResponse = response?.toMutableMap()
                updatePersonalData(mutableResponse, race)
                updateRace(mutableResponse?.toMutableMap(), listener)
            }

            override fun onFailure() {
                listener.onFailure()
            }
        })
    }

    private fun getHistory(listener: ResponseListener<Map<String, RaceResult>?>) {
        RetrofitClient.apiService.getBins(jsonApiId!!).enqueue(
            object : Callback<Map<String, RaceResult>> {
                override fun onResponse(
                    call: Call<Map<String, RaceResult>>,
                    response: Response<Map<String, RaceResult>>
                ) {
                    listener.onResponse(response.body())
                }

                override fun onFailure(call: Call<Map<String, RaceResult>>, t: Throwable) {
                    listener.onFailure()
                }
            })
    }

    private fun getAllUserRace(listener: ResponseListener<List<UserRace>>) {
        getHistory(object : ResponseListener<Map<String, RaceResult>?> {
            override fun onResponse(response: Map<String, RaceResult>?) {
                val userRace = arrayListOf<UserRace>()
                response?.let { it ->
                    for (entry in it) {
                        userRace.add(convertToUserRace(entry.value))
                    }
                }
                listener.onResponse(userRace)
            }

            override fun onFailure() {
                listener.onFailure()
            }
        })
    }

    private fun updatePersonalData(data: MutableMap<String, RaceResult>?, race: Race) {
        FirebaseAuthManager.getCurrentUser()?.let { fbUser ->
            val personalInfo = data?.get(fbUser.uid)
            if (personalInfo == null) { //add at first time
                val raceResult = RaceResult().also {
                    it.email = fbUser.email
                    it.history.add(race)
                }
                data?.put(fbUser.uid, raceResult)
            } else { //update exist info
                data.get(fbUser.uid)?.history?.add(race)
            }
        }
    }

    private fun convertToUserRace(recent: RaceResult): UserRace {
        var totalWpm = 0.0
        var lastPlayedTime = 0L
        val racerResultList = recent.history
        for (racerResult in racerResultList) {
            totalWpm = totalWpm.plus(racerResult.wpm)
            racerResult.time?.let {
                if (it > lastPlayedTime) {
                    lastPlayedTime = it
                }
            }
        }
        val average = totalWpm / racerResultList.size
        val displayName = TextHelper.emailToDisplayName(recent.email) ?: "missed name"
        return UserRace(displayName, average.toDecimal2(), lastPlayedTime)
    }

    private fun createJsonApiId(cb: (String?) -> Unit?) {
        RetrofitClient.apiService.postBins().enqueue(
            object : Callback<PostResponse> {
                override fun onResponse(call: Call<PostResponse>, res: Response<PostResponse>) {
                    val splitResponse = res.body()?.uri?.split("/")
                    val id = splitResponse?.get(splitResponse.size - 1)
                    cb(id)
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {

                }
            })
    }

    private fun updateRace(
        params: Map<String, RaceResult>?,
        listener: ResponseListener<Map<String, RaceResult>?>
    ) {
        RetrofitClient.apiService.putBins(jsonApiId!!, params).enqueue(
            object : Callback<Map<String, RaceResult>> {
                override fun onResponse(
                    call: Call<Map<String, RaceResult>>,
                    res: Response<Map<String, RaceResult>>
                ) {
                    listener.onResponse(res.body())
                }

                override fun onFailure(call: Call<Map<String, RaceResult>>, t: Throwable) {
                    listener.onFailure()
                }
            })
    }

}