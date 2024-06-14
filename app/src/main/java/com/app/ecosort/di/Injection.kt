package com.app.ecosort.di

import android.content.Context
import com.app.ecosort.api.ApiConfig
import com.app.ecosort.data.UserRepository
import com.app.ecosort.data.pref.UserPreference
import com.app.ecosort.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }

        ApiConfig.setAuthToken(user.token)

        val apiService = ApiConfig.getAuthService()

        return UserRepository.getInstance(pref, apiService)
    }
}
