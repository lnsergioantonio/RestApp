package com.lnsergioantonio.restapp

import android.app.Application
import com.lnsergioantonio.restapp.di.AppContainer

class App: Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}