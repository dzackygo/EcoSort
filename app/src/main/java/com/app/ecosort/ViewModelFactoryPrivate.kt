package com.app.ecosort

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.ecosort.view.gallery.GalleryViewModel
import com.app.ecosort.view.history.HistoryDetailActivity
import com.app.ecosort.view.history.HistoryViewModel

class ViewModelFactoryPrivate private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactoryPrivate? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactoryPrivate {
            if (INSTANCE == null) {
                synchronized(ViewModelFactoryPrivate::class.java) {
                    INSTANCE = ViewModelFactoryPrivate(application)
                }
            }
            return INSTANCE as ViewModelFactoryPrivate
        }

    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(mApplication) as T
        }
        else if (modelClass.isAssignableFrom(HistoryDetailActivity::class.java)) {
            return HistoryViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}