package com.android.sj.presentation.mappers

import android.content.Context
import com.android.sj.domain.ApiResult
import com.android.sj.domain.mappers.BaseMapper
import com.android.sj.domain.models.NaverMapData
import com.android.sj.presentation.models.uimodels.navermap.ReverseGeoUIData
import com.android.sj.presentation.utils.mapAddressConvert
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NaverMapPresentationMapper @Inject constructor(
    private val context: Context
) : BaseMapper() {
    fun domainToUIReverseGeoCo(flow: Flow<ApiResult<NaverMapData>>): Flow<ApiResult<ReverseGeoUIData>> {
        return apiResultMapper(flow) {
            ApiResult.Success(
                ReverseGeoUIData(
                    mapAddress = it.mapAddressConvert(context),
                    centerX = it.centerX.toString(),
                    centerY = it.centerY.toString()
                )
            )
        }
    }
}