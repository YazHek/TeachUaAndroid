package com.softserve.teachUaApp

import android.app.Application
import com.softserve.teachUaApp.repository.ClubRepository
import com.softserve.teachUaApp.service.impl.ClubServicelmpl

open class Container : Application() {

    private var instance: Container? = null
    private var findRepository: ClubRepository = ClubRepository()
    private lateinit var clubServicelmpl: ClubServicelmpl
    fun get(): Container? {

        return instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

     fun getClubService():ClubServicelmpl{
        clubServicelmpl = ClubServicelmpl(findRepository, )

        return clubServicelmpl
    }


}