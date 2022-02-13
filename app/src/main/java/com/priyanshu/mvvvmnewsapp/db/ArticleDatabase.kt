package com.priyanshu.mvvvmnewsapp.db

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.priyanshu.mvvvmnewsapp.model.Article

@Database(
    version = 2,
    entities = [Article::class]
)

@TypeConverters(Convertors::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao

    companion object{
        @Volatile
        private var instance:ArticleDatabase ?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){

            instance?: createDatabase(context).also{ instance = it}

        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
             context.applicationContext,
             ArticleDatabase::class.java,
             "article-db.db"
            ).fallbackToDestructiveMigration().build()


    }

}