package com.example.baustclubh.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.firestore.FirebaseFirestore

class FirstTimeSetup(private val context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val db = FirebaseFirestore.getInstance()

    fun checkAndSetup(onComplete: (Boolean) -> Unit) {
        val isFirstTime = prefs.getBoolean("is_first_time", true)

        if (isFirstTime) {
            setupDatabase {
                if (it) {
                    prefs.edit().putBoolean("is_first_time", false).apply()
                    onComplete(true)
                } else {
                    onComplete(false)
                }
            }
        } else {
            onComplete(true)
        }
    }

    private fun setupDatabase(onComplete: (Boolean) -> Unit) {
        // Users Data
        val users = mapOf(
            "admin" to mapOf(
                "studentId" to "admin",
                "name" to "Admin User",
                "email" to "admin@baust.edu.bd",
                "department" to "Administration",
                "batch" to "N/A",
                "password" to "admin123",
                "role" to "admin",
                "isActive" to true,
                "registeredClubs" to emptyList<String>()
            ),
            "202410001" to mapOf(
                "studentId" to "202410001",
                "name" to "Hasibul Hasib",
                "email" to "hasib@baust.edu.bd",
                "department" to "CSE",
                "batch" to "11th",
                "password" to "123456",
                "role" to "student",
                "isActive" to true,
                "registeredClubs" to listOf("CSE Programming Club")
            )
        )

        // Clubs Data
        val clubs = mapOf(
            "club1" to mapOf(
                "clubId" to "club1",
                "clubName" to "CSE Programming Club",
                "description" to "Programming and coding club",
                "category" to "Technical",
                "icon" to "💻",
                "totalMembers" to 156,
                "moderatorId" to "admin",
                "isActive" to true,
                "memberList" to listOf("202410001")
            ),
            "club2" to mapOf(
                "clubId" to "club2",
                "clubName" to "BAUST Robotics Club",
                "description" to "Build robots and compete",
                "category" to "Technical",
                "icon" to "🤖",
                "totalMembers" to 84,
                "moderatorId" to "admin",
                "isActive" to true,
                "memberList" to emptyList<String>()
            )
        )

        // Events Data
        val events = mapOf(
            "event1" to mapOf(
                "eventId" to "event1",
                "eventName" to "Coding Contest 2026",
                "clubId" to "club1",
                "clubName" to "CSE Programming Club",
                "date" to "2026-03-22",
                "time" to "10:00 AM",
                "venue" to "Computer Lab",
                "isRegistrationOpen" to true,
                "registeredStudents" to emptyList<String>()
            ),
            "event2" to mapOf(
                "eventId" to "event2",
                "eventName" to "Robotics Workshop",
                "clubId" to "club2",
                "clubName" to "BAUST Robotics Club",
                "date" to "2026-03-25",
                "time" to "2:00 PM",
                "venue" to "Lab 502",
                "isRegistrationOpen" to true,
                "registeredStudents" to emptyList<String>()
            )
        )

        // Applications Data
        val applications = mapOf(
            "app1" to mapOf(
                "applicationId" to "app1",
                "studentId" to "202410001",
                "studentName" to "Hasibul Hasib",
                "clubId" to "club1",
                "clubName" to "CSE Programming Club",
                "reason" to "I want to improve my coding skills",
                "status" to "approved",
                "appliedDate" to "2026-03-10"
            )
        )

        // Resources Data
        val resources = mapOf(
            "res1" to mapOf(
                "resourceId" to "res1",
                "title" to "C Programming Notes",
                "type" to "Note",
                "category" to "CSE",
                "fileUrl" to "https://firebasestorage.googleapis.com/example/notes.pdf",
                "uploadedBy" to "admin",
                "downloadCount" to 0,
                "isFree" to true
            )
        )

        // Save all to Firestore
        val batch = db.batch()

        users.forEach { (id, data) ->
            val ref = db.collection("users").document(id)
            batch.set(ref, data)
        }

        clubs.forEach { (id, data) ->
            val ref = db.collection("clubs").document(id)
            batch.set(ref, data)
        }

        events.forEach { (id, data) ->
            val ref = db.collection("events").document(id)
            batch.set(ref, data)
        }

        applications.forEach { (id, data) ->
            val ref = db.collection("applications").document(id)
            batch.set(ref, data)
        }

        resources.forEach { (id, data) ->
            val ref = db.collection("resources").document(id)
            batch.set(ref, data)
        }

        batch.commit().addOnSuccessListener {
            onComplete(true)
        }.addOnFailureListener {
            onComplete(false)
        }
    }
}