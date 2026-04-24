package com.example.baustclubh.data.model

data class Club(
    val clubId: String = "",
    val clubName: String = "",
    val description: String = "",
    val category: String = "", // Technical, Cultural, Sports, etc.
    val icon: String = "👥",
    val totalMembers: Int = 0,
    val moderatorId: String = "",
    val moderatorName: String = "",
    val establishedDate: String = "",
    val isActive: Boolean = true,
    val memberList: List<String> = emptyList(),
    val eventList: List<String> = emptyList()
)