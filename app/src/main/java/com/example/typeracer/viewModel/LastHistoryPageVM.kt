package com.example.typeracer.viewModel

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.example.typeracer.R
import com.example.typeracer.adapter.RaceHistoryAdapter
import com.example.typeracer.repo.RaceRepo
import com.example.typeracer.repo.ResponseListener
import com.example.typeracer.repo.model.UserRace

class LastHistoryPageVM(app: Application, raceRepo: RaceRepo) : BaseVM(app) {

    val showProgress = ObservableBoolean(true)
    val raceHistoryAdapter: ObservableField<RaceHistoryAdapter> = ObservableField()

    init {
        raceRepo.getLasts(listener = object : ResponseListener<List<UserRace>> {
            override fun onResponse(response: List<UserRace>) {
                showProgress.set(false)
                if (!response.isNullOrEmpty()) {
                    raceHistoryAdapter.set(RaceHistoryAdapter(response))
                }
            }

            override fun onFailure() {
                showProgress.set(false)
                Toast.makeText(getApplication(), R.string.error_msg, Toast.LENGTH_LONG).show()
            }
        })
    }

}