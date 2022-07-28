package com.example.newsapp.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun convertToArticleDate(publishedDate: String?): Date{
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(publishedDate)
}