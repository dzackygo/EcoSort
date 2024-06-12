package com.app.ecosort.api

import com.app.ecosort.response.LoginResult
import com.app.ecosort.response.NewsResponse
import com.app.ecosort.response.UploadResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface UploadApiService {
    @Multipart
    @POST("app/images")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): UploadResponse

    @POST("app/images")
    fun getImage(
        @Path("data") data: String
    ): Call<UploadResponse>

}