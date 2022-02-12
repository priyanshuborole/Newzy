package com.priyanshu.mvvvmnewsapp.repository

import androidx.room.Query
import com.priyanshu.mvvvmnewsapp.api.RetrofitInstance
import com.priyanshu.mvvvmnewsapp.db.ArticleDatabase
import com.priyanshu.mvvvmnewsapp.model.Article

class NewsRepository(
    val db : ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode : String, pageNumber : Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchNews(searchQuery, pageNumber)

    suspend fun upsert(article : Article) =
        db.getArticleDao().upsertArticle(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun delete(article: Article) =
        db.getArticleDao().deleteArticle(article)

}