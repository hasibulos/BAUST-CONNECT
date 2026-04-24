package com.example.baustclubh.data.model

data class Resource(
    val resourceId: String = "",
    val title: String = "",
    val type: String = "", // Book, Note, Slide, Question, Video
    val category: String = "", // CSE, EEE, BBA, etc.
    val subject: String = "",
    val semester: String = "",
    val fileUrl: String = "",
    val uploadedBy: String = "",
    val uploadDate: String = "",
    val downloadCount: Int = 0,
    val isFree: Boolean = true
)