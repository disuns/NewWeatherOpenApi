package com.example.presentation.mappers

import com.example.presentation.models.CoinUiModel
import com.test.domain.ApiResult
import com.test.domain.models.CoinDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object CoinPresentationMapper {
    fun domainToUI(coinFlow:Flow<ApiResult<CoinDomainModel>>): Flow<ApiResult<CoinUiModel>> {
        return coinFlow.map { coin->
            when(coin){
                is ApiResult.Success -> ApiResult.Success(CoinUiModel(id = coin.value.id))
                is ApiResult.Empty -> ApiResult.Empty
                is ApiResult.Loading -> ApiResult.Loading
                is ApiResult.Error -> ApiResult.Error(coin.code, coin.exception)
            }
        }
    }

    fun uiToDomain(coin:CoinUiModel): CoinDomainModel{
        return CoinDomainModel(
            id = coin.id
        )
    }
}