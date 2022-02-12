package com.priyanshu.mvvvmnewsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.priyanshu.mvvvmnewsapp.model.Article
import retrofit2.http.DELETE


@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertArticle(article: Article) : Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}