package com.aruel.mvvmnewsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aruel.mvvmnewsapp.repository.EventRepository

@Suppress("UNCHECKED_CAST")

class EventViewModelProviderFactory(val eventRepository: EventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventViewModel(eventRepository) as T
    }
}