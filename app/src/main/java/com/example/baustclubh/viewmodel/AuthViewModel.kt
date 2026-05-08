//package com.example.baustclubh.viewmodel
//
//import android.app.Application
//import android.content.Context
//import android.util.Log
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.baustclubh.data.model.User
//import com.example.baustclubh.data.repository.AuthRepository
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//// --- Login & Registration States ---
//sealed class LoginState {
//    object Idle : LoginState()
//    object Loading : LoginState()
//    data class Success(val user: User) : LoginState()
//    data class Error(val message: String) : LoginState()
//}
//
//sealed class RegisterState {
//    object Idle : RegisterState()
//    object Loading : RegisterState()
//    data class Success(val user: User) : RegisterState()
//    data class Error(val message: String) : RegisterState()
//}
//
//class AuthViewModel(application: Application) : AndroidViewModel(application) {
//    private val repository = AuthRepository()
//
//    // Persistent Login এর জন্য SharedPreferences
//    private val sharedPrefs = application.getSharedPreferences("baust_auth_prefs", Context.MODE_PRIVATE)
//
//    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
//    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()
//
//    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
//    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()
//
//    private val _currentUser = MutableStateFlow<User?>(null)
//    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
//
//    init {
//        // অ্যাপ ওপেন হওয়ার সাথে সাথে সেভ করা সেশন চেক করবে
//        checkSavedSession()
//    }
//
//    /**
//     * SharedPreferences থেকে সেশন চেক করে সরাসরি লগইন করানো
//     */
//    private fun checkSavedSession() {
//        val savedRole = sharedPrefs.getString("user_role", null)
//        if (savedRole != null) {
//            val user = User(
//                name = sharedPrefs.getString("user_name", "") ?: "",
//                email = sharedPrefs.getString("user_email", "") ?: "",
//                role = savedRole,
//                studentId = sharedPrefs.getString("user_id", "") ?: "",
//                department = sharedPrefs.getString("user_dept", "") ?: "",
//                batch = sharedPrefs.getString("user_batch", "") ?: ""
//            )
//            _currentUser.value = user
//            _loginState.value = LoginState.Success(user)
//            Log.d("AuthVM", "Auto-login successful for: ${user.name}")
//        }
//    }
//
//    /**
//     * Login Logic (Super Admin Username: hasib_admin)
//     */
//    fun login(userInput: String, password: String) {
//        viewModelScope.launch {
//            _loginState.value = LoginState.Loading
//
//            // ১. সুপার অ্যাডমিন স্পেশাল চেক (No Student ID needed)
//            if (userInput == "hasib_admin" && password == "admin1234") {
//                val superAdmin = User(
//                    name = "Hasibul Hasib",
//                    email = "hasibulhasiboffical@gmail.com",
//                    password = "admin1234",
//                    role = "super_admin",
//                    department = "All Central",
//                    studentId = "ADMIN_ROOT"
//                )
//                saveSession(superAdmin) // সেশন সেভ
//                _currentUser.value = superAdmin
//                _loginState.value = LoginState.Success(superAdmin)
//                return@launch
//            }
//
//            // ২. সাধারণ ইউজার বা অন্য অ্যাডমিনদের ডাটাবেজ চেক
//            try {
//                val user = repository.login(userInput, password)
//                if (user != null && user.password == password) {
//                    saveSession(user) // সেশন সেভ
//                    _currentUser.value = user
//                    _loginState.value = LoginState.Success(user)
//                } else {
//                    _loginState.value = LoginState.Error("Invalid Username or Password")
//                }
//            } catch (e: Exception) {
//                _loginState.value = LoginState.Error("Login Error: ${e.message}")
//            }
//        }
//    }
//
//    /**
//     * সেশন সেভ করার ফাংশন (যাতে অ্যাপ কাটলে লগআউট না হয়)
//     */
//    private fun saveSession(user: User) {
//        sharedPrefs.edit().apply {
//            putString("user_role", user.role)
//            putString("user_name", user.name)
//            putString("user_email", user.email)
//            putString("user_id", user.studentId)
//            putString("user_dept", user.department)
//            putString("user_batch", user.batch)
//            apply()
//        }
//    }
//
//    /**
//     * ডাইনামিক ডিপার্টমেন্ট এক্সেস কন্ট্রোল
//     */
//    fun canManageDept(targetDept: String): Boolean {
//        val user = _currentUser.value ?: return false
//        return when (user.role) {
//            "super_admin" -> true // সুপার অ্যাডমিন সব ডিপার্টমেন্টের এক্সেস পাবে
//            "dept_admin" -> user.department == targetDept // শুধু নিজের ডিপার্টমেন্ট
//            else -> false
//        }
//    }
//
//    /**
//     * ডাইনামিক ক্লাব এক্সেস কন্ট্রোল
//     */
//    fun canManageClub(clubName: String, clubDept: String): Boolean {
//        val user = _currentUser.value ?: return false
//        return when (user.role) {
//            "super_admin" -> true
//            "dept_admin" -> user.department == clubDept
//            "club_admin" -> user.managedClub == clubName
//            else -> false
//        }
//    }
//
//    fun isSuperAdmin(): Boolean = _currentUser.value?.role == "super_admin"
//
//    fun logout() {
//        sharedPrefs.edit().clear().apply() // ফোনের মেমোরি থেকে ডাটা মুছবে
//        _currentUser.value = null
//        _loginState.value = LoginState.Idle
//    }
//
//    fun register(user: User) {
//        viewModelScope.launch {
//            _registerState.value = RegisterState.Loading
//            try {
//                if (repository.isEmailAlreadyRegistered(user.email)) {
//                    _registerState.value = RegisterState.Error("Email already in use!")
//                } else {
//                    val success = repository.registerUser(user)
//                    if (success) _registerState.value = RegisterState.Success(user)
//                    else _registerState.value = RegisterState.Error("Registration failed")
//                }
//            } catch (e: Exception) {
//                _registerState.value = RegisterState.Error("Error: ${e.message}")
//            }
//        }
//    }
//
//    fun resetStates() {
//        _loginState.value = LoginState.Idle
//        _registerState.value = RegisterState.Idle
//    }
//}
package com.example.baustclubh.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
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

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AuthRepository()
    private val sharedPrefs = application.getSharedPreferences("baust_auth_prefs", Context.MODE_PRIVATE)

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    init {
        checkSavedSession()
    }

    private fun checkSavedSession() {
        val savedRole = sharedPrefs.getString("user_role", null)
        if (savedRole != null) {
            val user = User(
                name = sharedPrefs.getString("user_name", "") ?: "",
                email = sharedPrefs.getString("user_email", "") ?: "",
                role = savedRole,
                studentId = sharedPrefs.getString("user_id", "") ?: "",
                department = sharedPrefs.getString("user_dept", "") ?: "",
                batch = sharedPrefs.getString("user_batch", "") ?: ""
            )
            _currentUser.value = user
            _loginState.value = LoginState.Success(user)
        }
    }

    /**
     * Updated Login Logic
     * এটি ইউজার এবং অ্যাডমিন—উভয়কেই হ্যান্ডেল করবে।
     */
    fun login(userInput: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            // ১. ডিফল্ট সুপার অ্যাডমিন চেক (সরাসরি কোড থেকে)
            if (userInput == "hasib_admin" && password == "admin1234") {
                val superAdmin = User(
                    name = "Hasibul Hasib",
                    email = "hasibulhasiboffical@gmail.com",
                    password = "admin1234",
                    role = "super_admin",
                    department = "All Central",
                    studentId = "ADMIN_ROOT"
                )
                saveSession(superAdmin)
                _currentUser.value = superAdmin
                _loginState.value = LoginState.Success(superAdmin)
                return@launch
            }

            // ২. রেজিস্ট্রেশন করা ইউজার বা ডাটাবেজের অ্যাডমিনদের চেক
            try {
                // repository.login-এ userInput (ID) এবং password পাঠানো হচ্ছে
                val user = repository.login(userInput, password)

                if (user != null) {
                    // পাসওয়ার্ড ভ্যালিডেশন
                    if (user.password == password) {
                        saveSession(user)
                        _currentUser.value = user
                        _loginState.value = LoginState.Success(user)
                        Log.d("AuthVM", "Login success for ID: $userInput, Role: ${user.role}")
                    } else {
                        _loginState.value = LoginState.Error("Incorrect password!")
                    }
                } else {
                    _loginState.value = LoginState.Error("ID not found. Please Register if you are a student.")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Database Error: ${e.message}")
                Log.e("AuthVM", "Login Error: ${e.message}")
            }
        }
    }

    private fun saveSession(user: User) {
        sharedPrefs.edit().apply {
            putString("user_role", user.role)
            putString("user_name", user.name)
            putString("user_email", user.email)
            putString("user_id", user.studentId)
            putString("user_dept", user.department)
            putString("user_batch", user.batch)
            apply()
        }
    }

    fun logout() {
        sharedPrefs.edit().clear().apply()
        _currentUser.value = null
        _loginState.value = LoginState.Idle
    }

    fun register(user: User) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            try {
                // ইমেইল চেক করার পাশাপাশি স্টুডেন্ট আইডি অলরেডি আছে কি না তাও চেক করা উচিত (যদি রিপোজিটরিতে মেথড থাকে)
                val success = repository.registerUser(user)
                if (success) {
                    _registerState.value = RegisterState.Success(user)
                } else {
                    _registerState.value = RegisterState.Error("Registration failed")
                }
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error("Error: ${e.message}")
            }
        }
    }

    fun resetStates() {
        _loginState.value = LoginState.Idle
        _registerState.value = RegisterState.Idle
    }
}