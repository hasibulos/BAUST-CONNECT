package com.example.baustclubh.data.model

data class Club(
    val id: String = "",
    val name: String = "",
    val moderator: String = "",
    val email: String = "",
    val website: String = "",
    val department: String = "",
    val type: String = "",
    val imageUrl: String = "",
    val imageName: String = "",
    val description: String = "",
    val establishedDate: String = "",
    val totalMembers: Int = 0,
    // নতুন লিস্ট ফিল্ডগুলো যোগ করা হয়েছে
    val memberList: List<String> = emptyList(), // মেম্বারদের ইউজার আইডি এখানে থাকবে
    val eventList: List<String> = emptyList()   // ইভেন্টের আইডিগুলো এখানে থাকবে
)