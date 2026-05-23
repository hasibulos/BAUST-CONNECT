package com.example.baustclubh.data.model

data class Event(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val time: String = "",
    val location: String = "",
    val clubName: String = "",
    val imageUrl: String = "",
    val imageName: String = "",
    val category: String = "Tech",       // 🆕 নতুন যুক্ত করা হলো (ডিফল্ট ভ্যালু সহ)
    val type: String = "Seminar",        // 🆕 নতুন যুক্ত করা হলো (ডিফল্ট ভ্যালু সহ)
    val department: String = "CSE"       // 🆕 নতুন যুক্ত করা হলো (ডিফল্ট ভ্যালু সহ)
)