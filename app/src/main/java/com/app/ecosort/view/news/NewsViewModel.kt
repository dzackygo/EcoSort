package com.app.ecosort.view.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.ecosort.api.NewsItem
import com.app.ecosort.api.repository.NewsRepository

class NewsViewModel : ViewModel() {
    private val newsRepository = NewsRepository()
    private val _newsList = MutableLiveData<List<NewsItem>>()
    val newsList: LiveData<List<NewsItem>> = _newsList

    fun fetchHealthNews() {
        newsRepository.getGarbageNews(
            onSuccess = { newsList ->
                _newsList.postValue(newsList)
            },
            onFailure = { errorMessage ->
            }
        )
    }
}