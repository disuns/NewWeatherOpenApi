package com.android.sj.presentation.mappers

import android.content.Context
import com.android.sj.domain.mappers.BaseMapper
import com.android.sj.domain.models.NaverMapData
import com.android.sj.presentation.models.uimodels.navermap.ReverseGeoUIModel
import com.android.sj.presentation.utils.mapAddressConvert
import dagger.assisted.AssistedInject

class NaverMapPresentationMapper @AssistedInject constructor(
    private val context: Context
) : BaseMapper() {
    fun domainToUIReverseGeoCo(data: NaverMapData) = ReverseGeoUIModel(
        mapAddress = data.mapAddressConvert(context),
        centerX = data.centerX.toString(),
        centerY = data.centerY.toString()
    )
}