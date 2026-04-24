package com.example.baustclubh.data.model

data class Event(
    val eventId: String = "",
    val eventName: String = "",
    val clubId: String = "",
    val clubName: String = "",
    val description: String = "",
    val date: String = "",
    val time: String = "",
    val venue: String = "",
    val organizer: String = "",
    val maxParticipants: Int = 0,
    val registeredStudents: List<String> = emptyList(),
    val isRegistrationOpen: Boolean = true,
    val imageUrl: String = ""
)