package com.softserve.teachUaApp.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.softserve.teachUaApp.models.ClubModel
import com.softserve.teachUaApp.dto.search.AdvancedSearchClubProfile
import com.softserve.teachUaApp.dto.search.AllClubsProfile
import com.softserve.teachUaApp.mappers.toClub
import com.softserve.teachUaApp.retrofit.RetrofitService
import retrofit2.HttpException


private const val START_PAGE_CLUB = 0

class ClubsPageSource(
    private val service: RetrofitService,
    var allClubsProfile: AllClubsProfile,
    var advancedSearchClubProfile: AdvancedSearchClubProfile,
) : PagingSource<Int, ClubModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ClubModel> {
        // println("length " + MainActivity().cityName.length)
        val clubName = ""
        var cityName = allClubsProfile.cityName
        val isOnline = false
        val categoryName = ""
        val page = params.key ?: 0
        var pageSize = 8

        println(advancedSearchClubProfile.categoriesName)
        println("in datasource " + allClubsProfile.cityName)
        println("in datasource" + allClubsProfile.cityName.length)

        if (cityName.isEmpty()) {
            cityName = "Київ"
        }


        if (advancedSearchClubProfile.isAdvanced) {
            println("ADVANCED")

            val advancedSearchResponse =
                service.getClubsByAdvancedSearch(
                    name = advancedSearchClubProfile.name,
                    cityName = advancedSearchClubProfile.cityName,
                    districtName = advancedSearchClubProfile.districtName,
                    stationName = advancedSearchClubProfile.stationName,
                    sort = advancedSearchClubProfile.sort,
                    categoriesName = advancedSearchClubProfile.categoriesName,
                    isCenter = advancedSearchClubProfile.isCenter,
                    page = page

                )
            //  println(advancedSearchResponse.body()?.content.toString())
            if (advancedSearchResponse.isSuccessful) {

                val searchedClubs =
                    checkNotNull(advancedSearchResponse.body()).content.map { it.toClub() }
                //println(advancedSearchResponse.body()?.content?.get(0)?.categories)
//                if (searchedClubs.isEmpty()){
//                    return LoadResult.Error(HttpException(advancedSearchResponse))
//                }
                println("content " + searchedClubs.toString())
                println(page)
                val nextKey = if (searchedClubs.size < (pageSize) - 2) null else page + 1
                val prevKey = if (page == 0) null else page - 1
                return LoadResult.Page(searchedClubs, prevKey, nextKey)
            } else {
                println(advancedSearchResponse.errorBody())
                return LoadResult.Error(HttpException(advancedSearchResponse))
            }


        } else {

            println("DEFAULT")

            val allClubsResponse =
                service.getAllClubs(clubName = clubName,
                    cityName = cityName,
                    isOnline,
                    categoryName,
                    page = page)



            if (allClubsResponse.isSuccessful) {
                val clubs = checkNotNull(allClubsResponse.body()).content.map { it.toClub() }
                println(allClubsResponse.body()!!?.content[0].categories)
                println(page)
                val nextKey = if (clubs.size < pageSize) null else page + 1
                val prevKey = if (page == 0) null else page - 1
                return LoadResult.Page(clubs, prevKey, nextKey)
            } else {
                println(allClubsResponse.errorBody().toString())
                return LoadResult.Error(HttpException(allClubsResponse))
            }

        }
    }

    override fun getRefreshKey(state: PagingState<Int, ClubModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}