package com.example.baustclubh.data.model

data class Event(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val time: String = "",
    val location: String = "",
    val clubId: String = "",      // কোন ক্লাব ইভেন্টটি আয়োজন করছে
    val clubName: String = "",    // সহজে দেখানোর জন্য ক্লাবের নাম
    val imageUrl: String = "",
    val imageName: String = "",
    val attendeeList: List<String> = emptyList() // যারা জয়েন করবে তাদের আইডি
)