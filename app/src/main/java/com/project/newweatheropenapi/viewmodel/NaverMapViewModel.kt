package com.project.newweatheropenapi.viewmodel

import com.project.newweatheropenapi.network.repository.NaverMapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NaverMapViewModel @Inject constructor(private val repository: NaverMapRepository) : BaseViewModel() {
}