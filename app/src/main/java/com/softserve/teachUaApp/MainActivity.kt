package com.softserve.teachUaApp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softserve.teachUaApp.adapters.ClubsLoadStateAdapter
import com.softserve.teachUaApp.adapters.RCVAdapter
import com.softserve.teachUaApp.dto.search.AdvancedSearchClubProfile
import com.softserve.teachUaApp.dto.search.AllClubsProfile
import com.softserve.teachUaApp.dto.search.CategoryToUrlTransformer
import com.softserve.teachUaApp.mappers.toCategory
import com.softserve.teachUaApp.mappers.toCity
import com.softserve.teachUaApp.mappers.toDistrict
import com.softserve.teachUaApp.mappers.toStation
import com.softserve.teachUaApp.models.ClubsViewModel
import com.softserve.teachUaApp.retrofit.Common
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.adv_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity() : AppCompatActivity(), View.OnClickListener {


    lateinit var progressDialog: ProgressDialog

    private lateinit var clubsViewModel: ClubsViewModel
    lateinit var clubsAdapter: RCVAdapter
    var query = ""
    private var districtByCity = ""
    private var stationByCity = ""
    var recreationCount = 0
    var service = Common.retrofitService
    lateinit var dialog: Dialog
    var allClubsProfile = AllClubsProfile(query, "", false, "", 0)
    var categories = arrayListOf<String>()
    var listOfSearchedCategories = arrayListOf<String>()
    var cities = arrayListOf<String>()
    var districts = arrayListOf<String>()
    var stations = arrayListOf<String>()
    var checkboxCounter = 0

    var advancedSearchClubProfile =
        AdvancedSearchClubProfile("",
            null,
            "",
            "",
            "",
            "name,asc",
            0,
            emptyList(),
            isOnline = false,
            isCenter = false,
            isAdvanced = false)

    var layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressDialog = ProgressDialog(this)
        createAdvancedSearchDialog()
        searchAdvBtn.setOnClickListener(this)
        searchBtn.setOnClickListener(this)
        searchEdit.setOnClickListener(this)
        searchEdit.setOnClickListener(this)
        clearEdit.setOnClickListener(this)



        createViewModel()


        rcv.layoutManager = layoutManager
        clubsAdapter = RCVAdapter(this)
        rcv.adapter = clubsAdapter.withLoadStateHeaderAndFooter(
            header = ClubsLoadStateAdapter { clubsAdapter.retry() },
            footer = ClubsLoadStateAdapter { clubsAdapter.retry() }
        )


        clubsAdapter.setOnClickListener(object : RCVAdapter.ClickListener {
            override fun onClick(pos: Int, aView: View) {
//
            }
        })

        addDataToVM()

        clubsAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        setSupportActionBar(toolbar)

        setUpNavigationDrawer()

//        searchEdit.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
//
//            if (b){
//
//            } else
//                hideSoftKeyboard(searchEdit)
//        }


        dialog.club_adv_search_radioBtn.isChecked = true
        dialog.center_adv_search_radioBtn.isChecked = false
        dialog.isOnline.isChecked = false

        println("cities" + cities)

        lifecycleScope.launch {
            districts.add("Виберіть район")
            stations.add("Виберіть станцію")
            differentThread()
            citySpinnerPicker()

            clubsAdapter.loadStateFlow.collectLatest { loadStates ->


                when (loadStates.refresh) {

                    is LoadState.Loading -> {

                        println("Loading Case")
                        whenLoadingClubs()
                    }

                    is LoadState.Error -> {
                        println("Error Case")
                        whenErrorLoadingClubs()
                    }


                    else -> {
                        println("Else Case")
                        dismissProgressDialog()
                        if (clubsAdapter.itemCount < 1) {
                            whenDataIsClear()
                            println("clubs ada" + clubsAdapter.itemCount)
                        }
                        println("NotLoading Case")
                        rcv.isInvisible = false
                        //error_text.isVisible = false
                    }
                }
            }


        }
    }

    private fun Activity.hideSoftKeyboard(editText: EditText) {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(editText.windowToken, 0)
        }
    }

    private fun createAdvancedSearchDialog() {
        dialog = Dialog(this, R.style.CustomAlertDialog)
        dialog.setContentView(R.layout.adv_search)
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )


    }

    private fun edd() {

        //searchEdit.setIc
    }

    @SuppressLint("ClickableViewAccessibility")
    fun EditText.setupClearButtonWithAction() {

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                var clearIcon = if (editable?.isNotEmpty() == true) R.drawable.ic_clear else 0
                setCompoundDrawablesWithIntrinsicBounds(0, 0, clearIcon, 0)


            }


            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        })

        setOnClickListener {


        }


    }


    private fun whenLoadingClubs() {
        showLoadingListDialog()
        error_text.isVisible = false

    }

    private fun whenErrorLoadingClubs() {

        dismissProgressDialog()
        rcv.isInvisible = true
        error_text.isVisible = true
    }

    private fun whenDataIsClear() {

        println("Data Is Clear")
        dismissProgressDialog()
        rcv.isInvisible = true
        error_text.text = "Cant load matching results for your search"
        error_text.isVisible = true

    }

    private fun createViewModel() {
        println("recreated")
        allClubsProfile.cityName = query

        var factory =
            ClubsViewModelFactory(service, allClubsProfile, advancedSearchClubProfile)
        println("factory " + factory.toString())
        recreationCount++
        clubsViewModel = ViewModelProvider(this, factory).get(ClubsViewModel::class.java)


    }

    private fun updateDistricts() {

        lifecycleScope.launch {
            districts.clear()
            districts.add("Виберіть район")
            getDistrictByCityName(districtByCity)
        }
    }

    private fun updateStations() {

        lifecycleScope.launch {
            stations.clear()
            stations.add("Виберіть станцію")
            getStationByCityName(districtByCity)
        }
    }

    private fun addDataToVM() {

        lifecycleScope.launch {

            clubsViewModel.clubs.collectLatest { pagingData ->
                clubsAdapter.submitData(pagingData)


            }


        }


    }


    private fun setUpNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private suspend fun getAllCategories() {

        val categoriesResponse = service.getAllCategories()

        if (categoriesResponse.isSuccessful) {

            var categoriesList = checkNotNull(categoriesResponse.body()).map { it.toCategory() }

            for (category in categoriesList.reversed()) {
                categories.add(category.categoryName)

            }

        }
    }


    private suspend fun getAllCities() {

        val citiesResponse = service.getAllCities()

        if (citiesResponse.isSuccessful) {

            var citiesList = checkNotNull(citiesResponse.body()).map { it.toCity() }

            for (city in citiesList) {
                cities.add(city.cityName)

            }

        }
    }

    private suspend fun getDistrictByCityName(cityName: String) {

        val districtsResponse = service.getDistrictsByCityName(cityName)

        println("bodyyyy " + districtsResponse.body())
        if (districtsResponse.isSuccessful) {

            var districtsList = checkNotNull(districtsResponse.body()).map { it.toDistrict() }


            for (district in districtsList) {
                districts.add(district.districtName)
                // println(district.districtName)
            }
            println(districts.toString())
        }
    }

    private suspend fun getStationByCityName(cityName: String) {


        println("cityyNamee " + cityName)

        val stationsResponse = service.getStationsByCityName(cityName)

        if (stationsResponse.isSuccessful) {

            var stationsList = checkNotNull(stationsResponse.body()).map { it.toStation() }


            for (station in stationsList) {
                stations.add(station.stationName)
            }

            println(stations.toString())
        }
    }


    private fun showLoadingListDialog() {
        progressDialog = ProgressDialog(this@MainActivity)

        progressDialog.setTitle("Loading List Of Clubs")
        progressDialog.setMessage("List of clubs is loading, please wait")
        progressDialog.show()


    }

    private fun dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss()
        }
    }

    private suspend fun differentThread() = withContext(Dispatchers.IO) {
        getAllCities()
        getAllCategories()
        getDistrictByCityName(districtByCity)
        getStationByCityName(districtByCity)
        println("cuurr thread " + Thread.currentThread().id)
    }


    private fun citySpinnerPicker() {

        //this.text1.textAlignment = View.TEXT_ALIGNMENT_CENTER

        var citySpinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
            R.layout.item_dropdown, cities)

        citySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_cities.adapter = citySpinnerAdapter

        spinner_cities.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, id: Long) {
                println(parent!!.getItemAtPosition(pos))
                viewModelStore.clear()
                query = parent.getItemAtPosition(pos).toString()
                Toast.makeText(this@MainActivity,
                    parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show()
                advancedSearchClubProfile.isAdvanced = false
                createViewModel()
                addDataToVM()
                clubsAdapter.refresh()


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


        }

    }

    private fun setUpDefaultSpinner(
        dialog: Dialog,
        arrayRes: List<String>,
        @IdRes spinner: Int,
    ) {
        var sp: Spinner = dialog.findViewById(spinner)

        var citySearchSpinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
            R.layout.item_dropdown, arrayRes)
        citySearchSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp.adapter = citySearchSpinnerAdapter


    }


    private fun searchAdvDialog() {

        if (checkboxCounter != categories.size) {

            for (i in 0 until categories.size) {
                val checkBox =
                    LayoutInflater.from(dialog.context)
                        .inflate(R.layout.category_checkbox, rootAdvView, false) as CheckBox
                checkBox.text = categories[i]
                checkBox.setOnClickListener {
                    if (checkBox.isChecked) {
                        listOfSearchedCategories.add(CategoryToUrlTransformer().toUrlEncode(checkBox.text.toString()))
                    } else
                        listOfSearchedCategories.remove(CategoryToUrlTransformer().toUrlEncode(
                            checkBox.text.toString()))
                }
                dialog.rootAdvView.addView(checkBox, 12)
                checkboxCounter++
            }

        }

        dialog.show()

        setUpDefaultSpinner(dialog, cities, R.id.spinner_search_city)
        dialog.spinner_search_city.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    pos: Int,
                    id: Long,
                ) {

                    println("pos" + parent?.getItemAtPosition(pos))
                    districtByCity = parent?.getItemAtPosition(pos).toString()
                    advancedSearchClubProfile.cityName = districtByCity
                    updateDistricts()
                    updateStations()

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        setUpDefaultSpinner(dialog, districts, R.id.spinner_city_district)
        dialog.spinner_city_district.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    pos: Int,
                    id: Long,
                ) {

                    println("pos" + parent?.getItemAtPosition(pos))
                    var district = parent?.getItemAtPosition(pos).toString()
                    if (pos > 0) {
                        advancedSearchClubProfile.districtName = district
                    } else
                        advancedSearchClubProfile.districtName = null


                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }


        setUpDefaultSpinner(dialog, stations, R.id.spinner_metro_station)

        dialog.spinner_metro_station.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    pos: Int,
                    id: Long,
                ) {

                    println("pos" + parent?.getItemAtPosition(pos))
                    var station = parent?.getItemAtPosition(pos).toString()
                    if (pos > 0) {
                        advancedSearchClubProfile.stationName = station
                    } else
                        advancedSearchClubProfile.stationName = null


                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        dialog.apply_search.setOnClickListener {

            println("categories $listOfSearchedCategories")
            advancedSearchClubProfile.categoriesName = listOfSearchedCategories
            advancedSearchClubProfile.isAdvanced = true
            advancedSearchClubProfile.isCenter = false
            advancedSearchClubProfile.sort = "name,asc"
            dialog.hide()
            viewModelStore.clear()
            createViewModel()
            addDataToVM()
            rcv.smoothScrollToPosition(0)
            categories.clear()
            clubsAdapter.refresh()
            layoutManager.scrollToPositionWithOffset(0, 0)


        }

        dialog.clear_search.setOnClickListener {

            advancedSearchClubProfile.isAdvanced = false
            advancedSearchClubProfile.name = ""
            advancedSearchClubProfile.cityName = allClubsProfile.cityName
            advancedSearchClubProfile.districtName = null.toString()
            advancedSearchClubProfile.stationName = null.toString()
            dialog.club_adv_search_radioBtn.isChecked = true
            dialog.center_adv_search_radioBtn.isChecked = false
            dialog.isOnline.isChecked = false
//
            //searchEdit.text.clear()
            categories.clear()
            dialog.cancel()
            viewModelStore.clear()
            createViewModel()
            addDataToVM()
            clubsAdapter.refresh()
            rcv.smoothScrollToPosition(0)
        }

    }


    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun View.showKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.searchAdvBtn -> {
                searchAdvDialog()
                Toast.makeText(this, "fddsfds", Toast.LENGTH_SHORT).show()
            }

            R.id.searchBtn -> {
                viewModelStore.clear()
                advancedSearchClubProfile.isAdvanced = true
                advancedSearchClubProfile.name = searchEdit.text.toString()
                allClubsProfile.clubName
                advancedSearchClubProfile.cityName = query
                advancedSearchClubProfile.districtName = null
                advancedSearchClubProfile.stationName = null
                createViewModel()
                addDataToVM()
                clubsAdapter.refresh()
                searchEdit.isActivated = false
                layoutManager.scrollToPositionWithOffset(0, 0)
                hideKeyboard(searchEdit)
                searchEdit.clearFocus()

            }

            R.id.clearEdit -> {
                when (searchEdit.text?.isNotEmpty()) {

                    true -> {


                        searchEdit.text.clear()
                        searchEdit.isActivated = false
                        searchEdit.clearAnimation()
                        advancedSearchClubProfile.name = ""
                        advancedSearchClubProfile.isAdvanced = false
                        advancedSearchClubProfile.sort = ""
                        clubsAdapter.refresh()
                        searchEdit.clearFocus()


                    }

                    false -> {

                        searchEdit.requestFocus()
                        searchEdit.isActivated = true

                        //showKeyboard()
                    }
                }

            }


        }
    }


}

