package com.alaishat.mohammad.clean.docdoc.presentation.feature.doctor_details

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.repo.DoctorsRepo
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateViewModel
import com.alaishat.mohammad.clean.docdoc.presentation.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import androidx.core.net.toUri
import com.alaishat.mohammad.clean.docdoc.R

/**
 * Created by Mohammad Al-Aishat on Apr/17/2025.
 * Clean DocDoc Project.
 */
@HiltViewModel
class DoctorDetailsViewModel @Inject constructor(
    private val doctorsRepo: DoctorsRepo,
    private val stateDelegate: StateDelegate<DoctorDetailsUIState>,
    val savedStateHandle: SavedStateHandle,
) : ViewModel(), StateViewModel<DoctorDetailsUIState> by stateDelegate {
    companion object {
        const val DOCTOR_ID_KEY =
            "doctorId" // don't set the key like this: DOCTOR_ID_KEY, it will be invalid...
    }

    val doctorId: Int = savedStateHandle[DOCTOR_ID_KEY] ?: -1

    init {
        stateDelegate.setDefaultState(DoctorDetailsUIState.Loading)
        fetchDoctorDetails()
    }

    fun fetchDoctorDetails() {
        stateDelegate.updateState { DoctorDetailsUIState.Loading }
        viewModelScope.launch {
            val response = doctorsRepo.getDoctorById(doctorId)
            when (response) {
                is Resource.Error -> stateDelegate.updateState { DoctorDetailsUIState.Error(response.error) }
                is Resource.Success -> stateDelegate.updateState {
                    DoctorDetailsUIState.Success(
                        response.data
                    )
                }
            }
        }
    }

    fun shareDoctorLink(context: Context) {
        val url = "${NavigationRoute.DOCTOR_URL}/$doctorId"
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
        }
        val chooser = Intent.createChooser(intent, context.getString(R.string.share_doctor_link))
        context.startActivity(chooser)
    }
}
