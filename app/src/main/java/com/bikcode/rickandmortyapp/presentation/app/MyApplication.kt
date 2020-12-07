package com.bikcode.rickandmortyapp.presentation.app

import android.app.Application
import com.bikcode.rickandmortyapp.presentation.di.networkModule
import com.bikcode.rickandmortyapp.presentation.di.repositoryModule
import com.bikcode.rickandmortyapp.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            androidLogger(Level.ERROR)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}