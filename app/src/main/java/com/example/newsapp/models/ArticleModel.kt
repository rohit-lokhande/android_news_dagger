package com.example.newsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var source: SourceModel?,
    var author: String?,
    var title: String?,
    var description: String?,
    var url: String?,
    var urlToImage: String?,
    var publishedAt: String?,
    var content: String?)
