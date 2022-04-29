package com.softserve.teachUaApp.retrofit

import com.softserve.teachUaApp.dto.global.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitService {

//    @GET("api/clubs/search?clubName=&cityName=%D0%9A%D0%B8%D1%97%D0%B2&isOnline=false&categoryName=&")
//    fun getAllClubs(@Query("page") page: Int): Call<AllCardsDescriptionDto>

    @GET("api/clubs/search?")
    suspend fun getAllClubs(
        @Query("clubName")
        clubName: String? = "",
        @Query("cityName")
        cityName: String? = "Київ",
        @Query("isOnline")
        isOnline: Boolean? = false,
        @Query("categoryName")
        categoryName: String? = "",
        @Query("page")
        page: Int? = 0,
    ): Response<AllCardsDescriptionDto>


    @JvmSuppressWildcards
    @GET("api/clubs/search/advanced?")
    suspend fun getClubsByAdvancedSearch(
        @Query("name")
        name: String? = "",
        @Query("age")
        age: String? = null,
        @Query("cityName")
        cityName: String? = "Київ",
        @Query("districtName")
        districtName: String? = null,
        @Query("stationName")
        stationName: String? = null,
        @Query("categoriesName", encoded = true)
        categoriesName: List<String>? = null,
        @Query("isCenter")
        isCenter: Boolean? = null,
        @Query("isOnline")
        isOnline: Boolean? = null,
        @Query("sort")
        sort: String? = "name,asc",
        @Query("page")
        page: Int? = 0,
    ): Response<AllCardsDescriptionDto>

    @GET("api/cities")
    suspend fun getAllCities(): Response<List<CitiesDto>>

    @GET("api/categories")
    suspend fun getAllCategories(): Response<List<CategoriesDto>>

    @GET("api/districts/{cityName}")
    suspend fun getDistrictsByCityName(
       @Path("cityName")
        cityName: String? = null
    ): Response<List<DistrictsDto>>

    @GET("api/stations/{cityName}")
    suspend fun getStationsByCityName(
        @Path("cityName")
        cityName: String? = null
    ): Response<List<StationsDto>>


}