package com.app.ecosort.api

import com.app.ecosort.data.pref.LoginRequest
import com.app.ecosort.data.pref.RegisterRequest
import com.app.ecosort.response.LoginResponse
import com.app.ecosort.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/register")
    suspend fun register(
        @Body requestBody: RegisterRequest
    ): RegisterResponse

    @POST("auth/login")
    suspend fun login(
        @Body requestBody: LoginRequest
    ): LoginResponse
}