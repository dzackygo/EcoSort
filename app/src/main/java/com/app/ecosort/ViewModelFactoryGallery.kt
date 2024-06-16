package com.app.ecosort

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.ecosort.api.repository.UploadRepository
import com.app.ecosort.data.UserRepository
import com.app.ecosort.di.Injection
import com.app.ecosort.view.gallery.GalleryActivity
import com.app.ecosort.view.gallery.GalleryViewModel
import com.app.ecosort.view.home.MainViewModel
import com.app.ecosort.view.login.LoginViewModel
import com.app.ecosort.view.register.RegisterViewModel

class ViewModelFactoryGallery(private val uploadRepository: UploadRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GalleryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GalleryViewModel(uploadRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        private var instance: ViewModelFactoryGallery? = null

        fun getInstance(context: Context): ViewModelFactoryGallery {
            val uploadRepository = Injection.provideUploadRepository(context)
            if (instance == null) {
                instance = ViewModelFactoryGallery(uploadRepository)
            }
            return instance as ViewModelFactoryGallery
        }
    }
}