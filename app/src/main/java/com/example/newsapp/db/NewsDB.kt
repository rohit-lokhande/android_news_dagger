package com.example.newsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.models.ArticleModel

@Database(entities = [ArticleModel::class], version = 1)
@TypeConverters(SourceTypeConverters::class)
abstract class NewsDB : RoomDatabase(){

    abstract fun getNewsDAO() : NewsDao

}