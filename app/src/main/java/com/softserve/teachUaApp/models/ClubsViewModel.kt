package com.softserve.teachUaApp.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.softserve.teachUaApp.dto.search.AdvancedSearchClubProfile
import com.softserve.teachUaApp.dto.search.AllClubsProfile
import com.softserve.teachUaApp.pagination.ClubsPageSource
import com.softserve.teachUaApp.retrofit.RetrofitService


class ClubsViewModel(
    private val api: RetrofitService,
    private var allClubsProfile: AllClubsProfile,
    private var advancedSearchClubProfile: AdvancedSearchClubProfile
) : ViewModel() {

    val clubs = Pager(config = PagingConfig(pageSize = 2), pagingSourceFactory = {
        ClubsPageSource(api, allClubsProfile, advancedSearchClubProfile)


    }).flow.cachedIn(viewModelScope)




}