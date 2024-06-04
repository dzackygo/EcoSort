package com.app.ecosort.view.history

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.ecosort.api.HistoryRepository
import com.dicoding.asclepius.database.History

class HistoryViewModel(application: Application) : ViewModel() {

    private val mHistoryRepository: HistoryRepository = HistoryRepository(application)

    fun getAllNotes(): LiveData<List<History>> = mHistoryRepository.getAllNotes()

    fun insert(history: History){
        mHistoryRepository.insert(history)
    }
}