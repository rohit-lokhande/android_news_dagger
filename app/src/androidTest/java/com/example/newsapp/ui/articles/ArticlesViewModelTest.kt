package com.example.newsapp.ui.articles

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.newsapp.util.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
@SmallTest
class ArticlesViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var articlesRepository: ArticlesRepository

    lateinit var articlesViewModel: ArticlesViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        articlesViewModel = ArticlesViewModel(articlesRepository)
    }

    @Test
    fun fetchArticlesUsingCategory() = runTest {
        val articles = articlesViewModel.fetchArticlesByCategory("health").getOrAwaitValue()
        assertThat(articles.isNotEmpty()).isTrue()
    }
}