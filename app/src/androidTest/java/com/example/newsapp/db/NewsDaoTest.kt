package com.example.newsapp.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.newsapp.models.ArticleModel
import com.example.newsapp.models.SourceModel
import com.example.newsapp.util.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@ExperimentalCoroutinesApi
@SmallTest
class NewsDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var newsDB: NewsDB

    private lateinit var newsDao: NewsDao

    @Before
    fun setUp() {
        hiltRule.inject();
        newsDao = newsDB.getNewsDAO()
    }

    @Test
    fun insertArticle() = runTest {
        val article = ArticleModel(
            Math.random().toInt(),
            source = SourceModel("1","abc-news"),
            "author",
            "title",
            "desc",
            "url",
            "urlToImage",
            "2022-07-24T01:59:36Z",
            "content"
        )
        newsDao.addArticle(article)
        val articles = newsDao.getArticles().getOrAwaitValue().
            find {
                it.title == article.title
            }
        assertThat(articles != null).isTrue()
    }

    @Test
    fun deleteArticle() = runTest {
      val article = ArticleModel(
        Math.random().toInt(),
        source = SourceModel("1","abc-news"),
        "author",
        "title",
        "desc",
        "url",
        "urlToImage",
        "publishedAt",
        "content"
    )
        newsDao.delete(article)
        val articles = newsDao.getArticles().getOrAwaitValue()
        assertThat(articles).doesNotContain(article)
    }
}