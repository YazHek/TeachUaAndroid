package com.softserve.teachUaApp.json

import com.google.gson.Gson

class GsonDeserializer() {

     fun <T> deserialize(json: String?, clazz: Class<T>): T {
        return Gson().fromJson(json, clazz)
    }

    fun serialize(obj: Any): String {
        return Gson().toJson(obj)
    }

}