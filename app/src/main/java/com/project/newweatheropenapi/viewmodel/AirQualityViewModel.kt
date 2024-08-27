package com.project.newweatheropenapi.viewmodel

import androidx.lifecycle.viewModelScope
import com.project.newweatheropenapi.network.repository.AirQualityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirQualityViewModel @Inject constructor(private val repository: AirQualityRepository) : BaseViewModel() {
    fun abc(){
        viewModelScope.launch {
//            repository.requestAirQuality().collect{
//            }
        }
    }
}