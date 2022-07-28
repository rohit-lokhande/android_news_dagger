package com.example.newsapp.ui.articles

import androidx.lifecycle.LiveData
import com.example.newsapp.models.ArticleModel
import com.example.newsapp.models.BaseModel

interface ArticlesRepository {

    suspend fun fetchArticlesByCategory(category: String): BaseModel?

    suspend fun fetchTopHeadlines(): BaseModel?

    suspend fun markArticleAsRead(articleModel: ArticleModel)

     fun fetchReadArticles():LiveData<List<ArticleModel>>
}