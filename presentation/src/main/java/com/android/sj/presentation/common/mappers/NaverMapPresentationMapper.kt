package com.android.sj.presentation.common.mappers

import android.content.Context
import com.android.sj.domain.mappers.BaseMapper
import com.android.sj.domain.models.NaverMapData
import com.android.sj.presentation.models.uimodels.navermap.ReverseGeoUIModel
import com.android.sj.presentation.utils.mapAddressConvert
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NaverMapPresentationMapper @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseMapper() {
    fun domainToUIReverseGeoCo(data: NaverMapData) = ReverseGeoUIModel(
        mapAddress = data.mapAddressConvert(context),
        centerX = data.centerX.toString(),
        centerY = data.centerY.toString()
    )
}