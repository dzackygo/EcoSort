package com.app.ecosort.api

import com.app.ecosort.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Body;
import retrofit2.http.Header

interface UploadApiService {
    @Multipart
    @POST("app/images")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): UploadResponse
}