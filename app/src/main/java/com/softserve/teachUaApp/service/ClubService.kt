package com.softserve.teachUaApp.service

import com.softserve.teachUaApp.models.ClubModel

open interface ClubService {

    open fun create() {}
    open fun read() {}
    open fun update() {}
    open fun delete() {}
    fun getAll(): List<ClubModel>
    //fun getAll()
}