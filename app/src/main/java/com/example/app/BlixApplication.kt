package com.example.app

import android.app.Application
import com.example.app.data.DatabaseHelper.initDB
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BlixApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDB(applicationContext)
    }
}