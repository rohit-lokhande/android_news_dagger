package com.example.newsapp.ui.home.headlines

import androidx.lifecycle.LiveData
import com.example.newsapp.db.NewsDB
import com.example.newsapp.db.NewsDao
import com.example.newsapp.models.ArticleModel
import com.example.newsapp.models.BaseModel
import com.example.newsapp.network.ApiInterface
import javax.inject.Inject

class HeadlinesRepositoryImpl @Inject constructor(
    private val api: ApiInterface,
    private val newsDao: NewsDao
) : HeadlinesRepository {
    override suspend fun fetchTopHeadlines(): BaseModel? {
        return api.fetchTopHeadlines().body()
    }

    override suspend fun markArticleAsRead(articleModel: ArticleModel) {
        newsDao.addArticle(articleModel)
    }

    override suspend fun fetchReadArticles(): LiveData<List<ArticleModel>> {
        return newsDao.getArticles()
    }
}