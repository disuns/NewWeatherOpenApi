package com.project.newweatheropenapi.viewmodel

import com.project.newweatheropenapi.network.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) : BaseViewModel() {
}