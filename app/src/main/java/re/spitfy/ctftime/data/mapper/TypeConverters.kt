package re.spitfy.ctftime.data.mapper

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import re.spitfy.ctftime.data.Score


object TypeConverters {

    @JvmStatic
    @TypeConverter
    fun fromStringsToJsonString(strings: List<String>): String = Gson().toJson(strings)

    @JvmStatic
    @TypeConverter
    fun fromJsonStringToStrings(jsonString: String): List<String> =
            Gson().fromJson(jsonString, object : TypeToken<List<String>>(){}.type)

    @JvmStatic
    @TypeConverter
    fun fromScoresToJsonString(scores: Map<String, Score>): String = Gson().toJson(scores)

    @JvmStatic
    @TypeConverter
    fun fromJsonStringToScores(jsonString: String): Map<String, Score> =
            Gson().fromJson(jsonString, object: TypeToken<Map<String, Score>>(){}.type)

}