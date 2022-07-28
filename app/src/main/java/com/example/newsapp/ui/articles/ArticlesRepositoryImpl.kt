package com.example.newsapp.ui.articles

import androidx.lifecycle.LiveData
import com.example.newsapp.db.NewsDao
import com.example.newsapp.models.ArticleModel
import com.example.newsapp.models.BaseModel
import com.example.newsapp.network.ApiInterface
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(private val api: ApiInterface, private val newsDao: NewsDao) : ArticlesRepository {
    override suspend fun fetchArticlesByCategory(category: String): BaseModel? {
        return api.fetchTopHeadlines(category).body()
    }

    override suspend fun fetchTopHeadlines(): BaseModel? {
        return api.fetchTopHeadlines().body()
    }

    override suspend fun markArticleAsRead(articleModel: ArticleModel) {
        newsDao.addArticle(articleModel)
    }

    override  fun fetchReadArticles(): LiveData<List<ArticleModel>> {
        return newsDao.getArticles()
    }
}