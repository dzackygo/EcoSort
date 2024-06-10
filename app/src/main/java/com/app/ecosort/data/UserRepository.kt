    package com.app.ecosort.data

    import com.app.ecosort.api.AuthApiService
import com.app.ecosort.data.pref.UserModel
import com.app.ecosort.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

    class UserRepository private constructor(
        private val userPreference: UserPreference,
        private val authApiService: AuthApiService
    ) {

        suspend fun logout() {
            userPreference.logout()
        }
        fun getSession(): Flow<UserModel> {
            return userPreference.getSession()
        }

        suspend fun saveSession(user: UserModel) {
            return userPreference.saveSession(user)
        }

        suspend fun register(name: String, email: String, password: String) {
            try {
                val registerResponse = authApiService.register(name, email, password)

                if (registerResponse.error == true) {
                    throw RegistrationFailedException("Registration failed: ${registerResponse.message}")
                }
            } catch (e: Exception) {
                throw RegistrationFailedException("Registration failed: ${e.message}")
            }
        }
        suspend fun login(email: String, password: String) {
            try {
                val loginResponse = authApiService.login(email, password)

                if (loginResponse.error == true) {
                    throw LoginFailedException("Login failed: ${loginResponse.message}")
                }

                val token = loginResponse.loginResult?.token

                userPreference.saveSession(UserModel(email ?: "", token ?: "", true))
            } catch (e: Exception) {
                throw LoginFailedException("Login failed: ${e.message}")
            }
        }

        companion object {
            @Volatile
            private var instance: UserRepository? = null

            fun getInstance(userPreference: UserPreference, authApiService: AuthApiService): UserRepository =
                instance ?: synchronized(this) {
                    instance ?: UserRepository(userPreference, authApiService)
                }.also { instance = it }
        }
    }

    class RegistrationFailedException(message: String) : Exception(message)
    class LoginFailedException(message: String) : Exception(message)