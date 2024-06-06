package com.app.ecosort.view.login

import androidx.lifecycle.ViewModel
import com.app.ecosort.data.UserRepository
import com.app.ecosort.data.pref.UserModel
import kotlinx.coroutines.flow.Flow

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    suspend fun login(email: String, password: String) {
        repository.login(email, password)
    }

    fun getSession(): Flow<UserModel> {
        return repository.getSession()
    }
    suspend fun saveSession(user: UserModel) {
        return repository.saveSession(user)
    }
}