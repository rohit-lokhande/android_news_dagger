package com.example.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.models.ArticleModel

@Dao
interface NewsDao {
    @Insert()
    suspend fun addArticle(articleModel: ArticleModel)

    @Query("SELECT * FROM ArticleModel")
     fun getArticles() : LiveData<List<ArticleModel>>

    @Delete()
    fun delete(articleModel: ArticleModel)
}