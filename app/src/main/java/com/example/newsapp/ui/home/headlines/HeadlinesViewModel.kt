package com.example.newsapp.ui.home.headlines

import androidx.lifecycle.*
import com.example.newsapp.models.ArticleModel
import com.example.newsapp.network.NetworkState

import com.example.newsapp.ui.articles.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HeadlinesViewModel @Inject constructor(
    private val repository: ArticlesRepository
) : ViewModel() {
    private val _networkStateLiveData: MutableLiveData<NetworkState> =
        MutableLiveData<NetworkState>(NetworkState.LOADING)

    val networkStateLiveData: LiveData<NetworkState> = _networkStateLiveData

    fun fetchTopHeadlines(): LiveData<ArrayList<ArticleModel>> {
        return liveData {
            _networkStateLiveData.value = NetworkState.LOADING
            val response = repository.fetchTopHeadlines()
            _networkStateLiveData.value = NetworkState.SUCCESS
            response?.articles?.let { emit(it) }
        }
    }

    suspend fun markArticleAsRead(articleModel: ArticleModel) {
        repository.markArticleAsRead(articleModel)
    }

    fun fetchReadArticles(): LiveData<List<ArticleModel>> {
        _networkStateLiveData.value = NetworkState.LOADING
        val response = repository.fetchReadArticles()
        _networkStateLiveData.value = NetworkState.SUCCESS
        return response
    }
}