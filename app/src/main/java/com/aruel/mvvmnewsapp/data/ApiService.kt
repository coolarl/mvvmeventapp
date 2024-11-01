package com.aruel.mvvmnewsapp.data

import com.aruel.mvvmnewsapp.EventRespone
import com.aruel.mvvmnewsapp.EventResponeDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getEvent(
        @Query("active") active: Int,
        @Query("limit") limit: Int
    ): Response<EventRespone>

    @GET("search")
    suspend fun searchEvent(
        @Query("query") query: String,
        @Query("limit") limit: Int
    ): Response<EventRespone>

    @GET("events/{id}")
    suspend fun getEventDetail(@Path("id") eventId: Int): EventResponeDetail
}
