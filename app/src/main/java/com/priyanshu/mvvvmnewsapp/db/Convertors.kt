package com.priyanshu.mvvvmnewsapp.db

import androidx.room.TypeConverter
import com.priyanshu.mvvvmnewsapp.model.Source

class Convertors {

    @TypeConverter
    fun fromSource(source : Source): String {
        return source.name
    }
    @TypeConverter
    fun toSource(name : String) : Source{
        return Source(name,name)
    }
}