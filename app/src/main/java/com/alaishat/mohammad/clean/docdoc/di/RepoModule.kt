package com.alaishat.mohammad.clean.docdoc.di

import android.content.Context
import com.alaishat.mohammad.clean.docdoc.data.APIService
import com.alaishat.mohammad.clean.docdoc.data.SafeAPICaller
import com.alaishat.mohammad.clean.docdoc.data.repo.AppointmentsRepoImpl
import com.alaishat.mohammad.clean.docdoc.data.repo.AuthRepoImpl
import com.alaishat.mohammad.clean.docdoc.data.repo.AuthenticationCredentialsRepoImpl
import com.alaishat.mohammad.clean.docdoc.data.repo.CityRepoImpl
import com.alaishat.mohammad.clean.docdoc.data.repo.DoctorsRepoImpl
import com.alaishat.mohammad.clean.docdoc.data.repo.SpecializationsRepoImpl
import com.alaishat.mohammad.clean.docdoc.data.repo.UserLocalDataRepoImpl
import com.alaishat.mohammad.clean.docdoc.domain.repo.AppointmentsRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.AuthRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.AuthenticationCredentialsRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.CityRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.DoctorsRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.SpecializationsRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.UserLocalDataRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
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
    ): DoctorsRepo {
        return DoctorsRepoImpl(
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

    @Provides
    @Singleton
    fun provideSpecializationsRepo(
        safeAPICaller: SafeAPICaller,
        apiService: APIService
    ): SpecializationsRepo {
        return SpecializationsRepoImpl(
            safeAPICaller = safeAPICaller,
            apiService = apiService
        )
    }

    @Provides
    @Singleton
    fun provideCityRepo(
        safeAPICaller: SafeAPICaller,
        apiService: APIService
    ): CityRepo {
        return CityRepoImpl(
            safeAPICaller = safeAPICaller,
            apiService = apiService
        )
    }

    @Provides
    @Singleton
    fun provideAuthenticationCredentialsRepo(
        userLocalDataRepo: UserLocalDataRepo,
        authRepo: AuthRepo,
        coroutineScope: CoroutineScope,
    ): AuthenticationCredentialsRepo {
        return AuthenticationCredentialsRepoImpl(
            userLocalDataRepo = userLocalDataRepo,
            authRepo = authRepo,
            coroutineScope = coroutineScope
        )
    }
}
