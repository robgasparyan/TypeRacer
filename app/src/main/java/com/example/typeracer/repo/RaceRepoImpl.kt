package com.example.typeracer.repo

import android.util.Log
import com.example.typeracer.repo.rest.RetrofitClient
import com.example.typeracer.repo.model.PostResponse
import com.example.typeracer.repo.model.Race
import com.example.typeracer.repo.model.RaceResult
import com.example.typeracer.repo.model.UserRace
import com.example.typeracer.repo.prefs.Preferences
import com.example.typeracer.util.PrefConstants
import com.example.typeracer.util.sublistUntil
import com.example.typeracer.util.toDecimal2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RaceRepoImpl : RaceRepo {

    private val logTag = javaClass.canonicalName

    private var jsonApiId: String? = Preferences.getString(PrefConstants.MY_JSON_API_ID, "6x7qw")

    private var userId: String = "armen"

    override fun createJsonApiId() {
        if (jsonApiId.isNullOrEmpty()) {
            createJsonApiId {
                jsonApiId = it
                Preferences.putString(PrefConstants.MY_JSON_API_ID, jsonApiId)
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
                val historyList = response?.get(userId)?.history
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
                addNewItem(response, race)
                updateRace(response, listener)
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
                    Log.w(logTag, "getBins/ error message = ${t.message}")
                    listener.onFailure()
                }
            })
    }

    private fun getAllUserRace(listener: ResponseListener<List<UserRace>>) {
        getHistory(object : ResponseListener<Map<String, RaceResult>?> {
            override fun onResponse(response: Map<String, RaceResult>?) {
                val userRace = arrayListOf<UserRace>()
                response?.let { it ->
                    for ((key, value) in it) {
                        userRace.add(convertToUserRace(key, value))
                    }
                }
                listener.onResponse(userRace)
            }

            override fun onFailure() {
                listener.onFailure()
            }
        })
    }

    private fun addNewItem(data: Map<String, RaceResult?>?, race: Race) {
        data?.get(userId)?.history?.add(race)
    }

    private fun convertToUserRace(name: String, recent: RaceResult): UserRace {
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
        return UserRace(name, average.toDecimal2(), lastPlayedTime)
    }

    private fun createJsonApiId(cb: (String?) -> Unit?) {
        RetrofitClient.apiService.postBins().enqueue(
            object : Callback<PostResponse> {
                override fun onResponse(call: Call<PostResponse>, res: Response<PostResponse>) {
                    val splitResponse = res.body()?.uri?.split("/")
                    val id = splitResponse?.get(splitResponse.size - 1)
                    Log.d(logTag, "postBins/ jsonApiId = $id")
                    cb(id)
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    Log.w(logTag, "postBins/ error message = ${t.message}")
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
                    Log.w(logTag, "putBins/ error message = ${t.message}")
                    listener.onFailure()
                }
            })
    }

}