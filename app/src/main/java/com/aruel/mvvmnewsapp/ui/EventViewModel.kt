package com.aruel.mvvmnewsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aruel.mvvmnewsapp.EventRespone
import com.aruel.mvvmnewsapp.Events
import com.aruel.mvvmnewsapp.repository.EventRepository
import com.aruel.mvvmnewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class EventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    val breakingNews: MutableLiveData<Resource<EventRespone>> = MutableLiveData()
    private val breakingNewsLimit = 10

    val searchNews: MutableLiveData<Resource<EventRespone>> = MutableLiveData()
    private val searchNewsLimit = 10

    val pastEvents: MutableLiveData<Resource<EventRespone>> = MutableLiveData()
    private val pastEventsLimit = 10

    init {
        getBreakingNews(1)
    }
    fun getBreakingNews(active: Int) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = eventRepository.getEvent(active, breakingNewsLimit)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }
    fun searchNews(query: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = eventRepository.searchEvent(query,searchNewsLimit)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    fun getPastEvents() = viewModelScope.launch {
        pastEvents.postValue(Resource.Loading())
        val response = eventRepository.getPastEvents(pastEventsLimit)
        pastEvents.postValue(handlePastEventsResponse(response))
    }


    private fun handleBreakingNewsResponse(response: Response<EventRespone>): Resource<EventRespone> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleSearchNewsResponse(response: Response<EventRespone>) : Resource<EventRespone> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handlePastEventsResponse(response: Response<EventRespone>): Resource<EventRespone> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Events) = viewModelScope.launch {
        eventRepository.upsert(article)
    }

    fun getSavedNews() = eventRepository.getSavedNews()

    fun deleteArticle(article: Events) = viewModelScope.launch {
        eventRepository.deleteArticle(article)
    }


}
