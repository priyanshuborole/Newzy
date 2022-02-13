package com.priyanshu.mvvvmnewsapp.model

import com.priyanshu.mvvvmnewsapp.model.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)