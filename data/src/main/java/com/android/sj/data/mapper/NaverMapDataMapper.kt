package com.android.sj.data.mapper

import com.android.sj.common.utils.IoScope
import com.android.sj.data.network.response.navermap.NaverMapResponse
import com.android.sj.domain.ApiResult
import com.android.sj.domain.mappers.BaseMapper
import com.android.sj.domain.models.NaverMapData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

class NaverMapDataMapper @Inject constructor(
    @IoScope scope: CoroutineScope
) : BaseMapper(scope) {
    fun responseToDomainReverseGeoCo(response: Channel<ApiResult<NaverMapResponse>>): Channel<ApiResult<NaverMapData>> {
        return apiResultMapper(response) {
            if (it.status.code != 0) {
                ApiResult.Error(it.status.code)
            } else {
                val last = it.results.last()
                val center = last.region.area3.coords.center
                ApiResult.Success(
                    NaverMapData(
                        regionArea1Name = last.region.area1.name,
                        regionArea2Name = last.region.area2.name,
                        regionArea3Name = last.region.area3.name,
                        landName = last.land.name,
                        landNumber = last.land.number1,
                        resultName = last.name,
                        centerX = center.x,
                        centerY = center.y
                    )
                )
            }
        }
    }
}