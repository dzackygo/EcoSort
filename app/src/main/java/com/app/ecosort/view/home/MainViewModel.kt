package com.app.ecosort.view.home

import androidx.lifecycle.ViewModel
import com.app.ecosort.data.UserRepository
import com.app.ecosort.data.pref.UserModel
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    fun getSession(): Flow<UserModel> {
        return repository.getSession()
    }

    suspend fun saveSession(user: UserModel) {
        return repository.saveSession(user)
    }

    suspend fun logout() {
        repository.logout()
    }
}
