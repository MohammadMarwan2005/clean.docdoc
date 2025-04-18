package com.alaishat.mohammad.clean.docdoc.presentation.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.repo.DoctorsRepo
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Apr/18/2025.
 * Clean DocDoc Project.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val doctorsRepo: DoctorsRepo,
    private val stateDelegate: StateDelegate<SearchUIState>
) : ViewModel(), StateViewModel<SearchUIState> by stateDelegate {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun onStateFlowChange(newValue: String) {
        _searchQuery.value = newValue
    }

    init {
        stateDelegate.setDefaultState(SearchUIState.Initial)
        observeSearchQuery()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .drop(1)
                .debounce(700)
                .collectLatest { search() }
        }
    }

    fun search() {
        stateDelegate.updateState { SearchUIState.Loading }
        viewModelScope.launch {
            val response = doctorsRepo.searchForDoctors(query = searchQuery.value)
            when (response) {
                is Resource.Error -> {
                    stateDelegate.updateState { SearchUIState.Error(response.error) }
                }

                is Resource.Success -> {
                    stateDelegate.updateState { SearchUIState.Success(response.data) }
                }
            }
        }
    }
}
