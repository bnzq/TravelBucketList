package com.b00sti.travelbucketlist

import android.app.Application
import android.content.Context
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import timber.log.Timber

/**
 * @author Michał Palczyński
 * @since 12.12.2017.
 */
class App : Application() {

    init {
        instance = this
    }

    companion object {
        private lateinit var instance: App

        fun appCtx(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(getApplicationContext())
        AppEventsLogger.activateApp(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}