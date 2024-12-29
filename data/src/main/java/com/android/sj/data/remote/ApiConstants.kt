package com.android.sj.data.remote

object ApiConstants {
    /**
     * 단기예보
     */
    const val WEATHER = "/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst"
    const val TIME_WEATHER = "/1360000/VilageFcstInfoService_2.0/getVilageFcst"
    /**
     * 중기예보
     */
    const val WEEK_RAIN_SKY = "/1360000/MidFcstInfoService/getMidLandFcst"
    /**
     * 대기오염 정보
     */
    const val AIR_QUALITY_FRCST = "/B552584/ArpltnInforInqireSvc/getMinuDustFrcstDspth" //대기질 예보통보 조회
    const val RLTM_STATION = "/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty" //측정소별 실시간 측정정보 조회
    /**
     * 측정소
     */
    const val STATION_FIND = "/B552584/MsrstnInfoInqireSvc/getNearbyMsrstnList"
    /**
     * reverse geocode
     */
    const val MAP_REVERSE_GEOCODE = "/map-reversegeocode/v2/gc"
}