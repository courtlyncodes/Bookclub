package com.example.bookedandbusy

import android.app.Application
import com.example.bookedandbusy.data.AppContainer
import com.example.bookedandbusy.data.DefaultAppContainer

class BookedAndBusyApplication : Application() {
        /** AppContainer instance used by the rest of classes to obtain dependencies */
        lateinit var container: AppContainer
        override fun onCreate() {
            super.onCreate()
            container = DefaultAppContainer(this)
        }
    }
