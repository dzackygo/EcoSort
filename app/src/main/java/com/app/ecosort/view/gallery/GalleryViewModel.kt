package com.app.ecosort.view.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.app.ecosort.ResultState
import com.app.ecosort.api.repository.UploadRepository
import com.app.ecosort.response.UploadResponse
import java.io.File

class GalleryViewModel(private val uploadRepository: UploadRepository) : ViewModel() {
    fun uploadImage(image: File): LiveData<ResultState<UploadResponse>> {
        return uploadRepository.uploadImage(image)
    }
}