package com.softserve.teachUaApp.models


data class ClubModel(
    var clubId: Int,
    var clubName: String,
    var clubDescription: String,
    var clubImage: String,
    var clubBackgroundColor: String,
    var clubCategoryName: String
) {
}