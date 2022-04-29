package com.softserve.teachUaApp.dto.global

data class CurrentClubDescriptionDto(
    var id: Int,
    var name: String,
    var description: String,
    var urlLogo: String,
    var urlBackground: String,
    var categories: List<CategoriesDto>
) {


}
