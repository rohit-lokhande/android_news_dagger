package com.example.newsapp.ui.home.headlines

import androidx.lifecycle.LiveData
import com.example.newsapp.models.ArticleModel
import com.example.newsapp.models.BaseModel

interface HeadlinesRepository {


    suspend fun fetchTopHeadlines(): BaseModel?

    suspend fun markArticleAsRead(articleModel: ArticleModel)

    suspend fun fetchReadArticles():LiveData<List<ArticleModel>>
}