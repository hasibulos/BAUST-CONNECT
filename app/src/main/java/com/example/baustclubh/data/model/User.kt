package com.example.baustclubh.data.model

data class User(
    val studentId: String = "",
    val name: String = "",
    val email: String = "",
    val department: String = "",
    val batch: String = "",
    val password: String = "",
    val role: String = "student",
    val registeredClubs: List<String> = emptyList(),
    val profileImage: String = "",
    val joinDate: String = "",
    val isActive: Boolean = true
)