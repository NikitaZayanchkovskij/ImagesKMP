package com.mikitazayanchkouski.imageskmp

import android.app.Application
import com.mikitazayanchkouski.imageskmp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class PexelsApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(androidContext = this@PexelsApplication)
            androidLogger()
        }
    }
}