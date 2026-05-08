package com.example.baustclubh.data.model

data class User(
    val studentId: String = "",
    val name: String = "",
    val email: String = "",
    val department: String = "", // Student-er nijer dept
    val batch: String = "",
    val password: String = "",
    val role: String = "student", // "super_admin", "dept_admin", "club_admin", "student"

    // Role-based extra details
    val managedDept: String? = null, // Sudhu Dept Admin-er jonno (e.g., "CSE")
    val managedClub: String? = null, // Sudhu Club Admin-er jonno (e.g., "Computer Club")

    val registeredClubs: List<String> = emptyList(), // Student jekhane member
    val profileImage: String = "",
    val joinDate: String = "",
    val isActive: Boolean = true
)