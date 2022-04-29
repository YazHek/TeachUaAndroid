package com.softserve.teachUaApp.dto.search

data class AllClubsProfile(
    var clubName: String,
    var cityName: String,
    var isOnline: Boolean,
    var categoryName: String,
    var page: Int
) {
}