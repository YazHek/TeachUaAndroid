package com.softserve.teachUaApp.error

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.nio.channels.NoConnectionPendingException


class NetworkConnectionInterceptor(var context: Context) : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response  {
        if (!isConnected()){
            throw NoConnectivityException()
        }

        var builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }


    fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}