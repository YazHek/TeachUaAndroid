package com.softserve.teachUaApp.service.impl

import com.softserve.teachUaApp.models.ClubModel
import com.softserve.teachUaApp.adapters.RCVAdapter
import com.softserve.teachUaApp.repository.ClubRepository
import com.softserve.teachUaApp.service.ClubService

class ClubServicelmpl(clubRepository: ClubRepository) : ClubService{


    lateinit var adapter: RCVAdapter
    var cRepository: ClubRepository = clubRepository
    //var observer: OnUpdateListener = listener

    override fun create() {
    }

    override fun read() {
        super.read()
    }


    override fun getAll(): List<ClubModel> {

        return  cRepository.getAll()
    }

//    override fun getAll() {
//        return cRepository.getAll()
//    }

//
//    override fun registerListener(listener: OnUpdateListener) {
//        this.observer = listener
//    }
//
//    override fun removeListener(listener: OnUpdateListener) {
//        //observer = null
//    }
//
//    override fun notifyDataUpdated(allCardsDescriptionDto: AllCardsDescriptionDto) {
//        if (observer != null) {
//
//            observer.update(allCardsDescriptionDto)
//        }
//    }

}