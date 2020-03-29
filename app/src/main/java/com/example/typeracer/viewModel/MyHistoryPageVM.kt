package com.example.typeracer.viewModel

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.example.typeracer.R
import com.example.typeracer.adapter.MyRaceHistoryAdapter
import com.example.typeracer.repo.RaceRepo
import com.example.typeracer.repo.ResponseListener
import com.example.typeracer.repo.model.Race

class MyHistoryPageVM(app: Application, raceRepo: RaceRepo) : BaseVM(app) {

    var showProgress = ObservableBoolean(true)
    var myRaceHistoryAdapter: ObservableField<MyRaceHistoryAdapter> = ObservableField()

    init {
        raceRepo.getMyself(listener = object : ResponseListener<List<Race>?> {
            override fun onResponse(response: List<Race>?) {
                showProgress.set(false)
                myRaceHistoryAdapter.set(MyRaceHistoryAdapter(response))
            }

            override fun onFailure() {
                showProgress.set(false)
                Toast.makeText(getApplication(), R.string.error_msg, Toast.LENGTH_LONG).show()
            }
        })
    }
}