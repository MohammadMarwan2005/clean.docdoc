package com.alaishat.mohammad.clean.docdoc

import android.app.Application
import com.alaishat.mohammad.clean.docdoc.domain.repo.AuthenticationCredentialsRepo
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
@HiltAndroidApp
class DocdocApp: Application() {
    // we initialize it here because it will be used in the home screen when Guest Mode
    @Inject
    lateinit var authenticationCredentialsRepo: AuthenticationCredentialsRepo
    override fun onCreate() {
        super.onCreate()
        authenticationCredentialsRepo.authenticate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
