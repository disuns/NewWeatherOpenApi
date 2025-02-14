package com.android.sj.presentation.mappers

import android.content.Context
import com.android.sj.domain.ApiResult
import com.android.sj.domain.mappers.BaseMapper
import com.android.sj.domain.models.NaverMapData
import com.android.sj.presentation.models.uimodels.navermap.ReverseGeoUIModel
import com.android.sj.presentation.utils.mapAddressConvert
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

class NaverMapPresentationMapper @AssistedInject constructor(
    private val context: Context,
    @Assisted private val scope: CoroutineScope
) : BaseMapper(scope) {
    fun domainToUIReverseGeoCo(channel: Channel<ApiResult<NaverMapData>>): Channel<ApiResult<ReverseGeoUIModel>> {
        return apiResultMapper(channel) {
            ApiResult.Success(
                ReverseGeoUIModel(
                    mapAddress = it.mapAddressConvert(context),
                    centerX = it.centerX.toString(),
                    centerY = it.centerY.toString()
                )
            )
        }
    }
}