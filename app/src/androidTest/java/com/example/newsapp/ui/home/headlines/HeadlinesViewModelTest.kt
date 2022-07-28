package com.example.newsapp.ui.home.headlines

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.newsapp.models.ArticleModel
import com.example.newsapp.models.SourceModel
import com.example.newsapp.ui.articles.ArticlesRepository
import com.example.newsapp.util.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
@SmallTest
class HeadlinesViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var articlesRepository: ArticlesRepository

    lateinit var headlinesViewModel: HeadlinesViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        headlinesViewModel = HeadlinesViewModel(articlesRepository)
    }

    @Test
    fun fetchTopHeadlines() = runTest {
        val articles = headlinesViewModel.fetchTopHeadlines().getOrAwaitValue()
        assertThat(articles.isNotEmpty()).isTrue()
    }

    @Test
    fun fetchReadArticles() = runTest {
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
        headlinesViewModel.markArticleAsRead(article)
        val articles = headlinesViewModel.fetchReadArticles().getOrAwaitValue()
        assertThat(articles.isNotEmpty()).isTrue()
    }
}