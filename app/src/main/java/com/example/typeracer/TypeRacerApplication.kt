package com.example.typeracer

import android.app.Application
import android.content.Context
import com.example.typeracer.di.appModule
import com.example.typeracer.di.repoModule
import com.example.typeracer.di.viewModelModule
import com.example.typeracer.repo.RaceRepo
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.core.logger.Level

class TypeRacerApplication : Application() , KoinComponent {

    val repo by inject<RaceRepo>()

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@TypeRacerApplication)
            modules(appModule, viewModelModule, repoModule)
        }
        repo.createJsonApiId()
    }

    companion object {
        lateinit var appContext: Context
    }

}
