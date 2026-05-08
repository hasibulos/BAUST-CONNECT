package com.example.baustclubh.viewmodel

import androidx.lifecycle.ViewModel
import com.example.baustclubh.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    // Ei state-ta dashboard-er sob data hold korbe
    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState.asStateFlow()

    // Login success hole ba app open hole user info set korar jonno
    fun setUserData(user: User) {
        _userState.value = user
    }
}