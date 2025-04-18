package com.alaishat.mohammad.clean.docdoc.di

import com.alaishat.mohammad.clean.docdoc.presentation.common.EventDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.StringValidationManager
import com.alaishat.mohammad.clean.docdoc.presentation.feature.all_specs.AllSpecsUIState
import com.alaishat.mohammad.clean.docdoc.presentation.feature.appointment_info.AppointmentInfoUIState
import com.alaishat.mohammad.clean.docdoc.presentation.feature.appointments.AllAppointmentsUIState
import com.alaishat.mohammad.clean.docdoc.presentation.feature.auth.AuthEvent
import com.alaishat.mohammad.clean.docdoc.presentation.feature.auth.AuthUIState
import com.alaishat.mohammad.clean.docdoc.presentation.feature.book_appointment.BookAppointmentEvent
import com.alaishat.mohammad.clean.docdoc.presentation.feature.book_appointment.BookAppointmentUIState
import com.alaishat.mohammad.clean.docdoc.presentation.feature.doctor_details.DoctorDetailsUIState
import com.alaishat.mohammad.clean.docdoc.presentation.feature.home.HomeUIState
import com.alaishat.mohammad.clean.docdoc.presentation.feature.main.MainUIState
import com.alaishat.mohammad.clean.docdoc.presentation.feature.onboarding.OnboardingEvent
import com.alaishat.mohammad.clean.docdoc.presentation.feature.profile.ProfileEvent
import com.alaishat.mohammad.clean.docdoc.presentation.feature.profile.ProfileUIState
import com.alaishat.mohammad.clean.docdoc.presentation.feature.search.SearchUIState
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
    fun provideProfileUIState(): StateDelegate<ProfileUIState> = StateDelegate()

    @Provides
    @Singleton
    fun provideAllAppointmentsUIState(): StateDelegate<AllAppointmentsUIState> = StateDelegate()

    @Provides
    @Singleton
    fun provideAllSpecsUIState(): StateDelegate<AllSpecsUIState> = StateDelegate()

    @Provides
    @Singleton
    fun provideDoctorDetailsUIState(): StateDelegate<DoctorDetailsUIState> = StateDelegate()

    @Provides
    @Singleton
    fun provideBookAppointmentUIState(): StateDelegate<BookAppointmentUIState> = StateDelegate()

    @Provides
    @Singleton
    fun provideSearchUIState(): StateDelegate<SearchUIState> = StateDelegate()

    @Provides
    @Singleton
    fun provideAppointmentInfoUIState(): StateDelegate<AppointmentInfoUIState> = StateDelegate()

    @Provides
    @Singleton
    fun provideAuthEventDelegate(): EventDelegate<AuthEvent> = EventDelegate()

    @Provides
    @Singleton
    fun provideBookAppointmentEventDelegate(): EventDelegate<BookAppointmentEvent> = EventDelegate()

    @Provides
    @Singleton
    fun provideProfileEventDelegate(): EventDelegate<ProfileEvent> = EventDelegate()

    @Provides
    @Singleton
    fun provideOnboardingEventDelegate(): EventDelegate<OnboardingEvent> = EventDelegate()

    @Provides
    @Singleton
    fun provideStringValidationManager(): StringValidationManager = StringValidationManager()

}
