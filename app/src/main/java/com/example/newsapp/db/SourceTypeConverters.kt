package com.example.newsapp.db

import androidx.room.TypeConverter
import com.example.newsapp.models.ArticleModel
import com.example.newsapp.models.SourceModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken




class SourceTypeConverters {

    @TypeConverter
    fun languagesToStoredString(articleModel: ArticleModel): String {
        return Gson().toJson(articleModel)
    }

    @TypeConverter
    fun languagesToStoredString(articleModel: String): ArticleModel {
        return Gson().fromJson(articleModel,ArticleModel::class.java)
    }

    @TypeConverter
    fun languagesToSourceString(articleModel: SourceModel): String {
        return Gson().toJson(articleModel)
    }

    @TypeConverter
    fun languagesToSourceString(articleModel: String): SourceModel {
        return Gson().fromJson(articleModel,SourceModel::class.java)
    }

    @TypeConverter
    fun fromString(value: String?): ArrayList<ArticleModel?>? {
        val listType = object : TypeToken<ArrayList<ArticleModel?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<ArticleModel?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

}