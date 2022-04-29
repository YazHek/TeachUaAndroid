package com.softserve.teachUaApp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.softserve.teachUaApp.dto.search.AdvancedSearchClubProfile
import com.softserve.teachUaApp.dto.search.AllClubsProfile
import com.softserve.teachUaApp.models.ClubsViewModel
import com.softserve.teachUaApp.retrofit.RetrofitService

class ClubsViewModelFactory(
    private val api: RetrofitService,
    private var allClubsProfile: AllClubsProfile,
    private var advancedSearchClubProfile: AdvancedSearchClubProfile
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return ClubsViewModel(api, allClubsProfile, advancedSearchClubProfile) as T

    }
}