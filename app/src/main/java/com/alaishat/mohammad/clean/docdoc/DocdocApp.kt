package com.alaishat.mohammad.clean.docdoc

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
@HiltAndroidApp
class DocdocApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
