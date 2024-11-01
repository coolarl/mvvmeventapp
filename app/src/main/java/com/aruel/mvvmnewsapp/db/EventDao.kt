package com.aruel.mvvmnewsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aruel.mvvmnewsapp.Events

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Events): Long

    @Query("SELECT * FROM Event")
    fun getAllArticles(): LiveData<List<Events>>

    @Delete
    suspend fun deleteArticle(article: Events)
}