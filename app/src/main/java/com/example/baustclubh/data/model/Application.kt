package com.example.baustclubh.data.model

data class Application(
    val applicationId: String = "",
    val studentId: String = "",
    val studentName: String = "",
    val clubId: String = "",
    val clubName: String = "",
    val reason: String = "",
    val status: String = "pending", // pending, approved, rejected
    val appliedDate: String = "",
    val reviewedBy: String = "",
    val reviewDate: String = "",
    val reviewComment: String = ""
)