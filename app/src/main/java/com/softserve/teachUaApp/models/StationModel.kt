package com.softserve.teachUaApp.models

data class StationModel(
    var stationId: Int,
    var stationName: String,
    var cityName: String,
    var districtName: String?
) {
}