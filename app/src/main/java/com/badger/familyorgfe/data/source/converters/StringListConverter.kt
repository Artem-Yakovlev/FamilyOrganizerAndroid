package com.badger.familyorgfe.data.source.converters

import androidx.room.TypeConverter
import com.badger.familyorgfe.utils.moshiAdapter
import com.squareup.moshi.Types


object StringListConverter {

    private var type = Types.newParameterizedType(MutableList::class.java, String::class.java)
    private val stringListJsonAdapter = moshiAdapter.adapter<List<String>>(type)

    @TypeConverter
    fun toList(json: String?): List<String>? {
        return json?.let(stringListJsonAdapter::fromJson)
    }

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return stringListJsonAdapter.toJson(list)
    }
}