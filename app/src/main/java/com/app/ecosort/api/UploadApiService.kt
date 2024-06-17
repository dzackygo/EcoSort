package com.app.ecosort.api

import com.app.ecosort.response.UploadResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadApiService {
    @Multipart
    @POST("app/images")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): UploadResponse
}