package com.aruel.mvvmnewsapp.repository

import com.aruel.mvvmnewsapp.Events
import com.aruel.mvvmnewsapp.data.ApiConfig
import com.aruel.mvvmnewsapp.db.EventDatabase

class EventRepository(
    private val db: EventDatabase
) {
    suspend fun getEvent(active : Int, limit : Int) =
        ApiConfig.api.getEvent(active, limit)

    suspend fun getPastEvents(limit: Int) =
        ApiConfig.api.getEvent(active = 0, limit = limit)

    suspend fun searchEvent(query: String,limit: Int) =
        ApiConfig.api.searchEvent(query,limit)

    suspend fun upsert(article: Events) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Events) = db.getArticleDao().deleteArticle(article)
}