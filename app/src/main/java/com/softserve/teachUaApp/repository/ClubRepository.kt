package com.softserve.teachUaApp.repository


import com.softserve.teachUaApp.models.ClubModel

class ClubRepository {

    // private var observable: Observable = listener

    var isFinished: Boolean = false
    var clubList = mutableListOf<ClubModel>()


    fun create() {


    }

    fun getAll(): List<ClubModel> {
    return emptyList()
    }


//    fun getAll(): List<ClubModel> {
//
//        val page = 0
//
//        var apiService = Common.retrofitService
//        //println(Thread.currentThread().id)
//        //
//        var rs = AllCardsDescriptionDto(emptyArray())
//        println("main thread" + Thread.currentThread().id)
//
//        runBlocking {
//            apiService.getAllClubs(page).enqueue(
//                object : Callback<AllCardsDescriptionDto> {
//                    override fun onFailure(
//                        call: Call<AllCardsDescriptionDto>,
//                        t: Throwable,
//                    ) {
//
//                        t.printStackTrace()
//                        println("fdsnoooooooooot")
//
//                        isFinished = true
//                    }
//
//
//                    override fun onResponse(
//                        call: Call<AllCardsDescriptionDto>,
//                        response: Response<AllCardsDescriptionDto>,
//                    ) {
//                        println("main thread insd resp" + Thread.currentThread().id)
//                        //Thread.sleep(3000)
//                        rs = response.body() as AllCardsDescriptionDto
//                        var i = 0
//                        for (ele in rs.content) {
//
//                            clubList.add(
//                                i,
//                                ClubModel(
//                                    ele.id,
//                                    ele.name,
//                                    ele.description,
//                                    ele.categories[0].urlLogo,
//                                    ele.categories[0].backgroundColor
//                                )
//
//                            )
//                            i++
//
//
//                        }
//
//                        //  observable.notifyDataUpdated(rs)
//                        println("responce thread " + Thread.currentThread().id)
//
//
//                    }
//
//
//                }
//            )
//        }
//
//        return clubList
//
//    }
}


//    fun getAll(): List<ClubModel> {
//        var url =
//            "https://speak-ukrainian.org.ua/dev/api/clubs/search?clubName=&cityName=%D0%9A%DB80%B8%D1%97%D0%B2&isOnline=false&categoryName=&page=0"
//
//        var modeList = mutableListOf<ClubModel>()
//
//        var okHttpClient = OkHttpClient.Builder()
//            .connectTimeout(3, TimeUnit.SECONDS)
//            .readTimeout(3, TimeUnit.SECONDS)
//            .build()
//
//        var request = Request.Builder()
//            .url(url)
//            .get()
//            .build()
//        okHttpClient.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//                println("Failure " + call.isExecuted())
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                var i = 0
//                if (response.isSuccessful) {
//
//                    var contentOfEightClubs = GsonDeserializer().deserialize(
//                        response.body?.string(),
//                        AllCardsDescriptionDto::class.java
//                    )
//
//                    for (club in contentOfEightClubs.content) {
//
//                        var description = GsonDeserializer().deserialize(
//                            club.description,
//                            ClubDescriptionText::class.java
//                        )
//                        // var clubImg = GsonDeserializer().deserialize(contentOfEightClubs.content[0].categories[0].urlLogo, CategoriesDto::class.java)
//
//                        modeList.add(
//                            i,
//                            ClubModel(
//                                club.id,
//                                club.name,
//                                description.blocks[3].text,
//                                club.categories[0].urlLogo,
//                                club.categories[0].backgroundColor
//                            )
//                        )
//                        i++
//                        //println(contentOfEightClubs.content[i].name)
//                    }
//
//
//                }
//            }
//        })
//
//
//        return modeList
//    }


//    fun getAll(service: ClubServicelmpl): AllCardsDescriptionDto {
//        isFinished = false
//        var apiService = Common.retrofitService
//
//        var rs = AllCardsDescriptionDto(emptyArray())
//        println("main thread" + Thread.currentThread().id)
//        CoroutineScope(Dispatchers.IO).launch{
//
//            apiService.getAllClubs().enqueue(object : Callback<AllCardsDescriptionDto> {
//                override fun onFailure(call: Call<AllCardsDescriptionDto>, t: Throwable) {
//
//                    t.printStackTrace()
//                    println("fdsnoooooooooot")
//
//                    isFinished = true
//                }
//
//
//                override fun onResponse(
//                    call: Call<AllCardsDescriptionDto>,
//                    response: Response<AllCardsDescriptionDto>
//                ) {
//                    //Thread.sleep(3000)
//                    rs = response.body() as AllCardsDescriptionDto
//                    println(response.code())
//                    println(response.isSuccessful())
//                    println(rs.content.size)
//                    println("responseeee")
//                    println(rs.content[0].name.toString())
//
//
//                    println("responce thread " + Thread.currentThread().id)
//                    isFinished = true
//                    service.adapter = RCVAdapter(rs)
//
//                }
//
//
//            })
//        }
//
//        //val modelList = mutableListOf<AllCardsDescriptionDto>()
//
////        while (!isFinished){
////            println("In Prog")
////        }
//        println(rs.content.size)
//        return rs
//
//
//    }





