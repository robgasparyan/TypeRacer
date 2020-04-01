package com.example.typeracer

import android.app.Application
import com.example.typeracer.di.appModule
import com.example.typeracer.di.repoModule
import com.example.typeracer.di.viewModelModule
import com.example.typeracer.repo.RaceRepo
import com.example.typeracer.repo.prefs.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.core.logger.Level

class TypeRacerApplication : Application() , KoinComponent {

    override fun onCreate() {
        super.onCreate()

        Preferences.init(applicationContext)

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@TypeRacerApplication)
            modules(appModule, viewModelModule, repoModule)
        }
        val repo by inject<RaceRepo>()
        repo.createJsonApiId()
    }

}
