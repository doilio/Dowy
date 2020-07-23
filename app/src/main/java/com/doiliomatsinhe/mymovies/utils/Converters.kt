package com.doiliomatsinhe.mymovies.utils

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun listOfIntToJson(value: List<Int>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToListOfInt(value: String) = Gson().fromJson(value, Array<Int>::class.java).toList()

    @TypeConverter
    fun listOfStringToJson(value: List<String>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToListOfString(value: String) =
        Gson().fromJson(value, Array<String>::class.java).toList()
}