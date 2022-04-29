package com.softserve.teachUaApp.retrofit

import android.accounts.NetworkErrorException
import android.content.Context
import com.softserve.teachUaApp.MainActivity
import com.softserve.teachUaApp.error.NetworkConnectionInterceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HttpsURLConnection


object RetrofitClient {

    private var retrofit: Retrofit? = null




    fun getClient(baseUrl: String): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .build()
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                //.client(okHttpClient)
                .build()
        }
        return retrofit!!





    }
}