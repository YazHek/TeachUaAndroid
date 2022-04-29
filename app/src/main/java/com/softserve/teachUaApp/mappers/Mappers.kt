package com.softserve.teachUaApp.mappers

import com.softserve.teachUaApp.dto.global.*
import com.softserve.teachUaApp.models.*

internal fun CurrentClubDescriptionDto.toClub(): ClubModel {
    return ClubModel(
        clubId = id,
        clubName = name,
        clubDescription = description,
        clubImage = categories[0].urlLogo,
        clubBackgroundColor = categories[0].backgroundColor,
        clubCategoryName = categories[0].name
    )

}

internal fun CitiesDto.toCity(): CityModel {
    return CityModel(
        cityId = id,
        cityName = name,
        cityLatitude = latitude,
        cityLongtitude = longtitude
    )
}

internal fun DistrictsDto.toDistrict(): DistrictModel {
    return DistrictModel(
        districtId = id,
        districtName = name,
        cityName = cityName
    )
}

internal fun StationsDto.toStation(): StationModel {
    return StationModel(
        stationId = id,
        stationName = name,
        cityName = cityName,
        districtName = districtName
    )
}

internal fun CategoriesDto.toCategory(): CategoryModel {
    return CategoryModel(
        categoryId = id,
        categoryName = name,
        categoryDescription = description,
        categoryUrlLogo = urlLogo,
        categoryBackgroundColor = backgroundColor
    )
}