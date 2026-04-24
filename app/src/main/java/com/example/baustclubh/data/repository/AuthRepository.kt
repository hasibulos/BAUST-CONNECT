package com.example.baustclubh.data.repository

import android.util.Log
import com.example.baustclubh.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun login(studentId: String, password: String): User? {
        return try {
            Log.d("AuthRepo", "Trying to login with ID: $studentId")

            val snapshot = db.collection("users").document(studentId).get().await()

            if (snapshot.exists()) {
                val user = snapshot.toObject(User::class.java)
                Log.d("AuthRepo", "User found: ${user?.name}")
                Log.d("AuthRepo", "User password: ${user?.password}")
                user
            } else {
                Log.d("AuthRepo", "User not found for ID: $studentId")
                null
            }
        } catch (e: Exception) {
            Log.e("AuthRepo", "Login error: ${e.message}")
            null
        }
    }

    suspend fun registerUser(user: User): Boolean {
        return try {
            val existing = db.collection("users").document(user.studentId).get().await()
            if (existing.exists()) {
                false
            } else {
                db.collection("users").document(user.studentId).set(user).await()
                true
            }
        } catch (e: Exception) {
            false
        }
    }
}