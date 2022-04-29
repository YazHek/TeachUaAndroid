package com.softserve.teachUaApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_club_info.*

class ClubInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_info)
        println(clubNameText.text)




        getCurrentClubInfo()



    }

    private fun getCurrentClubInfo() {

        toolbar.title = intent.extras?.getString("clubName")
        clubNameText.text = intent.extras?.getString("clubName")
        clubDescriptionText.text = intent.extras?.getString("clubDescription")

        if(clubNameText.equals(null)){
            println("wtf???")
        }
//        intent.getIntExtra("clubName")
//        val gson = Gson()
//
//        var okHttpClient = OkHttpClient()
//        var currentClubName = intent.extras?.getInt("clubName")
//        currentClubName = currentClubName?.plus(1)
//        var url =
//            "https://speak-ukrainian.org.ua/dev/api/club/" + currentClubName.toString()
//        println(url)
//        var request = Request.Builder()
//            .url(url)
//            .get()
//            .build()
//        var response = okHttpClient.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful) {
//                    val resp = response.body?.string()
//                    println(resp)
//                    var clubDescriptionDto = gson.fromJson(resp, CurrentClubDescriptionDto::class.java)
//                    var name = clubDescriptionDto.name
//                    println(name)
//                    var description = clubDescriptionDto.description
//                    var desc: DescriptionText =
//                        gson.fromJson(description, DescriptionText::class.java)
//                    println(desc.blocks[3].text)
//                    this@ClubInfoActivity.runOnUiThread {
//                        clubNameText.text = name
//                        clubDescriptionText.text = desc.blocks[3].text
//                        toolbar?.title = clubNameText.toString()
//                    }
//                }
//            }
//        })


    }


}