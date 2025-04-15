package com.alaishat.mohammad.clean.docdoc.di

import android.content.Context
import com.alaishat.mohammad.clean.docdoc.data.APIService
import com.alaishat.mohammad.clean.docdoc.data.SafeAPICaller
import com.alaishat.mohammad.clean.docdoc.data.repo.AppointmentsRepoImpl
import com.alaishat.mohammad.clean.docdoc.data.repo.AuthRepoImpl
import com.alaishat.mohammad.clean.docdoc.data.repo.HomeRepoImpl
import com.alaishat.mohammad.clean.docdoc.data.repo.UserLocalDataRepoImpl
import com.alaishat.mohammad.clean.docdoc.domain.repo.AppointmentsRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.AuthRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.HomeRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.UserLocalDataRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideAuthRepo(apiService: APIService, safeAPICaller: SafeAPICaller): AuthRepo {
        return AuthRepoImpl(
            apiService = apiService,
            safeAPICaller = safeAPICaller
        )
    }

    @Provides
    @Singleton
    fun provideUserLocalDataRepo(
        @ApplicationContext context: Context
    ): UserLocalDataRepo {
        return UserLocalDataRepoImpl(
            context = context
        )
    }

    @Provides
    @Singleton
    fun provideHomeRepo(
        safeAPICaller: SafeAPICaller,
        apiService: APIService
    ): HomeRepo {
        return HomeRepoImpl(
            safeAPICaller = safeAPICaller,
            apiService = apiService
        )
    }

    @Provides
    @Singleton
    fun provideAppointmentsRepo(
        safeAPICaller: SafeAPICaller,
        apiService: APIService
    ): AppointmentsRepo {
        return AppointmentsRepoImpl(
            safeAPICaller = safeAPICaller,
            apiService = apiService
        )
    }
}
