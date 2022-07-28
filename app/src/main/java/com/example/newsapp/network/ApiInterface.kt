package com.example.newsapp.network

import com.example.newsapp.models.BaseModel
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Query

interface ApiInterface {

    @GET("top-headlines?country=in")
    suspend fun fetchTopHeadlines(): Response<BaseModel>

    @GET("top-headlines?country=in")
    suspend fun fetchTopHeadlines(@Query("category") sources: String): Response<BaseModel>
}