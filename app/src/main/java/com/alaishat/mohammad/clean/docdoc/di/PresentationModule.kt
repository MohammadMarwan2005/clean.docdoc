package com.alaishat.mohammad.clean.docdoc.di

import com.alaishat.mohammad.clean.docdoc.presentation.common.EventDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.StringValidationManager
import com.alaishat.mohammad.clean.docdoc.presentation.feature.auth.AuthEvent
import com.alaishat.mohammad.clean.docdoc.presentation.feature.auth.AuthUIState
import com.alaishat.mohammad.clean.docdoc.presentation.feature.home.HomeUIState
import com.alaishat.mohammad.clean.docdoc.presentation.feature.main.MainUIState
import com.alaishat.mohammad.clean.docdoc.presentation.feature.onboarding.OnboardingEvent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Mohammad Al-Aishat on Apr/13/2025.
 * Clean DocDoc Project.
 */
@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {
    @Provides
    @Singleton
    fun provideAuthStateDelegate(): StateDelegate<AuthUIState> = StateDelegate()

    @Provides
    @Singleton
    fun provideMainStateDelegate(): StateDelegate<MainUIState> = StateDelegate()

    @Provides
    @Singleton
    fun provideHomeStateDelegate(): StateDelegate<HomeUIState> = StateDelegate()

    @Provides
    @Singleton
    fun provideAuthEventDelegate(): EventDelegate<AuthEvent> = EventDelegate()

    @Provides
    @Singleton
    fun provideOnboardingEventDelegate(): EventDelegate<OnboardingEvent> = EventDelegate()

    @Provides
    @Singleton
    fun provideStringValidationManager(): StringValidationManager = StringValidationManager()

}
