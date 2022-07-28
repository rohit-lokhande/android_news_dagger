package com.example.newsapp.models

data class BaseModel(
    var status: String?,
    var totalResults: Int?,
    var articles: ArrayList<ArticleModel>?)
