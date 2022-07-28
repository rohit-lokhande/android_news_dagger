package com.example.newsapp.ui.articles

import androidx.lifecycle.*
import com.example.newsapp.db.NewsDao
import com.example.newsapp.models.ArticleModel

import com.example.newsapp.network.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val repository: ArticlesRepository) :
    ViewModel() {

    private val _networkStateLiveData: MutableLiveData<NetworkState> =
        MutableLiveData<NetworkState>(NetworkState.LOADING)


    val networkStateLiveData: LiveData<NetworkState> = _networkStateLiveData


    fun fetchArticlesByCategory(category: String): LiveData<ArrayList<ArticleModel>> {
        return liveData {
            _networkStateLiveData.value = NetworkState.LOADING
            val response = repository.fetchArticlesByCategory(category)
            _networkStateLiveData.value = NetworkState.SUCCESS
            response?.articles?.let { emit(it) }
        }
    }
}