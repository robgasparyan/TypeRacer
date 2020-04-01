package com.example.typeracer.viewModel

import android.app.Application
import androidx.databinding.ObservableField
import com.example.typeracer.adapter.HistoryPagerAdapter

class HistoryVM(app: Application, pagerAdapter: HistoryPagerAdapter) : BaseVM(app) {

    val historyPagerAdapter = ObservableField(pagerAdapter)

}