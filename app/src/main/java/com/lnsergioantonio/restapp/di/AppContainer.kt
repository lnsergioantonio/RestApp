package com.lnsergioantonio.restapp.di

import android.app.Application
import com.lnsergioantonio.restapp.BuildConfig
import com.lnsergioantonio.restapp.data.AppService
import com.lnsergioantonio.restapp.data.createNetworkClient
import com.lnsergioantonio.restapp.data.local.provideDatabase
import com.lnsergioantonio.restapp.data.local.provideRequestResponseDao
import com.lnsergioantonio.restapp.domain.GetResponseRepositoryImpl
import com.lnsergioantonio.restapp.domain.NetworkSourceImpl
import com.lnsergioantonio.restapp.domain.SendRequestRepositoryImpl
import com.lnsergioantonio.restapp.ext.NetworkHandler

class AppContainer(private val app: Application) {
    private val database = provideDatabase(app)
    private val dao = provideRequestResponseDao(database)

    private val apiService = createNetworkClient(BuildConfig.DEBUG).create(AppService::class.java)
    private val networkHandler = NetworkHandler(app)
    private val networkSource = NetworkSourceImpl(apiService)

    val sendRequestRepository = SendRequestRepositoryImpl(networkHandler, networkSource, dao)
    val getResponseRepository = GetResponseRepositoryImpl(dao)
}