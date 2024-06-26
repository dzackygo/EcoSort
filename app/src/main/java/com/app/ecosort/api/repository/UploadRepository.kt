package com.app.ecosort.api.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.app.ecosort.ResultState
import com.app.ecosort.api.ApiConfig
import com.app.ecosort.api.UploadApiService
import com.app.ecosort.response.UploadResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadRepository(
    private var uploadApiService: UploadApiService,
) {
    fun uploadImage(image: File): LiveData<ResultState<UploadResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val requestImageFile = image.asRequestBody("image/*".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Part.createFormData(
                "file",
                image.name,
                requestImageFile
            )
            uploadApiService = ApiConfig.getUploadService()
            val response = uploadApiService.uploadImage(multipartBody)
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: UploadRepository? = null
        fun getInstance(
            uploadApiService: UploadApiService,
        ): UploadRepository =
            instance ?: synchronized(this) {
                instance ?: UploadRepository(uploadApiService)
            }.also { instance = it }

    }
}