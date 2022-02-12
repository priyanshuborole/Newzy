package com.priyanshu.mvvvmnewsapp.model

import com.priyanshu.mvvvmnewsapp.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)