package com.alaishat.mohammad.clean.docdoc.presentation.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Doctor
import com.alaishat.mohammad.clean.docdoc.domain.repo.CityRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.DoctorsRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.SpecializationsRepo
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
import timber.log.Timber
import javax.inject.Inject
import kotlin.collections.filter
import kotlin.collections.map

/**
 * Created by Mohammad Al-Aishat on Apr/18/2025.
 * Clean DocDoc Project.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val doctorsRepo: DoctorsRepo,
    private val specializationsRepo: SpecializationsRepo,
    private val cityRepo: CityRepo,
    private val stateDelegate: StateDelegate<SearchUIState>
) : ViewModel(), StateViewModel<SearchUIState> by stateDelegate {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun onStateFlowChange(newValue: String) {
        _searchQuery.value = newValue
    }

    init {
        stateDelegate.setDefaultState(
            SearchUIState(
                filtersUIState = SearchFilterUIState.Loading,
                searchResultUIState = SearchResultUIState.Initial,
            )
        )
        fetchFilters()
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

    fun fetchFilters() {
        stateDelegate.updateState { it.copy(filtersUIState = SearchFilterUIState.Loading) }
        viewModelScope.launch {
            val specs = specializationsRepo.getAllSpecializations()
            val cities = cityRepo.getAllCities()
            when (specs) {
                is Resource.Error -> {
                    stateDelegate.updateState {
                        it.copy(
                            filtersUIState = SearchFilterUIState.Error(
                                specs.error
                            )
                        )
                    }
                }

                is Resource.Success -> {
                    when (cities) {
                        is Resource.Error -> {
                            stateDelegate.updateState {
                                it.copy(
                                    filtersUIState = SearchFilterUIState.Error(
                                        cities.error
                                    )
                                )
                            }
                        }

                        is Resource.Success -> {
                            stateDelegate.updateState {
                                it.copy(
                                    filtersUIState = SearchFilterUIState.Success(
                                        specs = specs.data.map {
                                            Selectable(
                                                isSelected = false,
                                                id = it.id,
                                                onClick = {
                                                    onSpecClicked(it.id)
                                                },
                                                label = it.name
                                            )
                                        }, cities = cities.data.map {
                                            Selectable(
                                                isSelected = false,
                                                id = it.id,
                                                onClick = {
                                                    onCityClicked(it.id)
                                                },
                                                label = it.name,
                                            )
                                        }
                                    ))
                            }
                        }
                    }
                }
            }
        }
    }

    fun search() {
        stateDelegate.updateState { it.copy(searchResultUIState = SearchResultUIState.Loading) }
        if (state.value.filtersUIState is SearchFilterUIState.Success) {
            viewModelScope.launch {
                val doctors = doctorsRepo.searchForDoctors(query = searchQuery.value)
                when (doctors) {
                    is Resource.Error -> {
                        stateDelegate.updateState {
                            it.copy(
                                searchResultUIState = SearchResultUIState.Error(
                                    doctors.error
                                )
                            )
                        }
                    }

                    is Resource.Success -> {
                        stateDelegate.updateState {
                            it.copy(
                                searchResultUIState = SearchResultUIState.Success(
                                    doctors.data.filterByFilters(
                                        state.value.filtersUIState as SearchFilterUIState.Success
                                    )
                                )
                            )
                        }
                    }
                }
            }
        } else {
            // we didn't load the filters yet!
        }
    }

    fun onSpecClicked(specId: Int) {
        val currentState = state.value.filtersUIState
        if (currentState is SearchFilterUIState.Success) {
            val newSpecs = currentState.specs.map {
                if (it.id == specId) it.copy(isSelected = !it.isSelected) else it
            }
            stateDelegate.updateState {
                it.copy(
                    filtersUIState = SearchFilterUIState.Success(
                        newSpecs,
                        cities = currentState.cities
                    )
                )
            }
        }
        search()
    }

    fun onCityClicked(cityId: Int) {
        val currentState = state.value.filtersUIState
        if (currentState is SearchFilterUIState.Success) {
            val newCities = currentState.cities.map {
                if (it.id == cityId) it.copy(isSelected = !it.isSelected) else it
            }
            stateDelegate.updateState {
                it.copy(
                    filtersUIState = SearchFilterUIState.Success(
                        specs = currentState.specs,
                        cities = newCities
                    )
                )
            }
        }
        search()
    }
}

data class Selectable(
    val isSelected: Boolean,
    val id: Int,
    val label: String,
    val onClick: () -> Unit,
    val key: String = "$label, $id"
)

fun List<Doctor>.filterByFilters(
    filterUIState: SearchFilterUIState.Success
): List<Doctor> {
    with(filterUIState) {
        val selectedSpecs = specs.filter { it.isSelected }
        val selectedCities = (cities.filter { it.isSelected })

        val filterSpecs = (if (selectedSpecs.isEmpty()) specs else selectedSpecs).map { it.id }
        val filterCities = (if (selectedCities.isEmpty()) cities else selectedCities).map { it.id }

        Timber.d("Filter Specs: $filterSpecs, cities: $filterCities")
        return filter { doctor ->

            val hasSpec: Boolean = filterSpecs.contains(doctor.specialization.id)
            val hasCity: Boolean = filterCities.contains(doctor.city.id)
            hasCity && hasSpec
        }
    }

}