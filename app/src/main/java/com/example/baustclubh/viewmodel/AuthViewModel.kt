package com.example.baustclubh.viewmodel
import android.util.Log  // ← এই লাইনটি যোগ করতে হবে

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baustclubh.data.model.User
import com.example.baustclubh.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val user: User) : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    data class Success(val user: User) : RegisterState()
    data class Error(val message: String) : RegisterState()
}

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun login(studentId: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val user = repository.login(studentId, password)
                if (user != null && user.password == password) {
                    _currentUser.value = user
                    _loginState.value = LoginState.Success(user)
                    Log.d("AuthVM", "Login Success: ${user.name}")
                } else {
                    _loginState.value = LoginState.Error("Invalid ID or Password")
                    Log.d("AuthVM", "Login Failed: Invalid credentials")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error: ${e.message}")
                Log.e("AuthVM", "Login error: ${e.message}")
            }
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            val success = repository.registerUser(user)
            if (success) {
                _registerState.value = RegisterState.Success(user)
            } else {
                _registerState.value = RegisterState.Error("Registration failed")
            }
        }
    }

    fun resetStates() {
        _loginState.value = LoginState.Idle
        _registerState.value = RegisterState.Idle
    }

    fun logout() {
        _currentUser.value = null
        _loginState.value = LoginState.Idle
    }
}