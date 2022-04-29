package com.softserve.teachUaApp.error

import java.io.IOException

class NoConnectivityException: IOException() {

    override val message: String
        get() = "No Internet Connection"
}