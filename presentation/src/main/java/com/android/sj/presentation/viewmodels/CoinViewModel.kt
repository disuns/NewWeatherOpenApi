package com.android.sj.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.presentation.mappers.CoinPresentationMapper
import com.example.presentation.models.CoinUiModel
import com.test.domain.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(private val getCoinUseCase: GetCoinUseCase) : ViewModel() {
    val state: StateFlow<ApiResult<CoinUiModel>> = CoinPresentationMapper
        .domainToUI(getCoinUseCase())
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.Lazily,
            initialValue = ApiResult.Loading
        )
}