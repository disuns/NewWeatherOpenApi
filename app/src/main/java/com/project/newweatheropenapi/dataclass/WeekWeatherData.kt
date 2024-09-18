package com.project.newweatheropenapi.dataclass

data class WeekWeatherData(
    var weekDate : WeekDate = WeekDate(),
    var rainAm:String = "",
    var rainPm:String = "",
    var skyAm:String = "",
    var skyPm:String = ""
)
