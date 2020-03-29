package com.example.typeracer.di

import androidx.fragment.app.FragmentManager
import com.example.typeracer.adapter.HistoryPagerAdapter
import com.example.typeracer.repo.RaceRepo
import com.example.typeracer.repo.RaceRepoImpl
import com.example.typeracer.viewModel.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { TypeVM(get(), get()) }
    viewModel { (fm: FragmentManager) -> HistoryVM(get(), get { parametersOf(fm) }) }
    viewModel { LastHistoryPageVM(get(), get()) }
    viewModel { TopHistoryPageVM(get(), get()) }
    viewModel { MyHistoryPageVM(get(), get()) }

}

val appModule = module {
    factory { (fm: FragmentManager) -> HistoryPagerAdapter(get(), fm) }
}

val repoModule = module {
    single<RaceRepo> { RaceRepoImpl() }
}