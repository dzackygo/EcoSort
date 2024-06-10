package com.app.ecosort.view.register

import androidx.lifecycle.ViewModel
import com.app.ecosort.data.UserRepository

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    suspend fun register(name: String, email: String, password: String) {
        userRepository.register(name, email, password)
    }
}
