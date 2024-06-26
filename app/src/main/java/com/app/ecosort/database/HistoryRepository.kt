package com.app.ecosort.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.asclepius.database.History
import com.dicoding.asclepius.database.HistoryDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryRepository(application: Application) {

    private val mNotesDao: HistoryDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = HistoryRoomDatabase.getDatabase(application)
        mNotesDao = db.historyDao()
    }

    fun getAllHistories(): LiveData<List<History>> = mNotesDao.getAllHistories()

    fun insert(history: History) {
        executorService.execute { mNotesDao.insert(history) }
    }
}