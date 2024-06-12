package com.app.ecosort.api.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.app.ecosort.ResultState
import com.app.ecosort.api.ApiConfig
import com.app.ecosort.api.UploadApiService
import com.app.ecosort.data.pref.UserPreference
import com.app.ecosort.response.UploadResponse
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadRepository(
    private var uploadApiService: UploadApiService,
    private var userPreference: UserPreference
) {
    fun uploadImage(image: File): LiveData<ResultState<UploadResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val token = userPreference.getToken().first()
            val requestImageFile = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
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
            userPreference: UserPreference
        ): UploadRepository =
            instance ?: synchronized(this) {
                instance ?: UploadRepository(uploadApiService, userPreference)
            }.also { instance = it }

    }
}