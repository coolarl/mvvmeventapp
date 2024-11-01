package com.aruel.mvvmnewsapp

data class EventRespone(
    val error: Boolean,
    val listEvents: List<Events>,
    val message: String
)