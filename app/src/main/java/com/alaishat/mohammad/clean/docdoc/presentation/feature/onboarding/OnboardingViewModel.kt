package com.alaishat.mohammad.clean.docdoc.presentation.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.clean.docdoc.domain.repo.UserLocalDataRepo
import com.alaishat.mohammad.clean.docdoc.presentation.common.EventDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.EventViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userLocalDataRepo: UserLocalDataRepo,
    private val eventDelegate: EventDelegate<OnboardingEvent>
) : ViewModel(), EventViewModel<OnboardingEvent> by eventDelegate {

    fun markOnboarded() {
        viewModelScope.launch {
            userLocalDataRepo.setOnboarded()
            eventDelegate.sendEvent(scope = this, newEvent = OnboardingEvent.NavigateToRegister)
        }
    }

}
