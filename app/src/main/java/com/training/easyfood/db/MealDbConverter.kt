package com.training.easyfood.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealDbConverter {

    @TypeConverter
    fun anyToString(attribute:Any?): String{

        if (attribute==null)
            return ""
        return attribute as String
    }
    @TypeConverter
    fun stringToAny(attribute: String?): Any{

        if (attribute==null)
            return ""
        return attribute
    }
}