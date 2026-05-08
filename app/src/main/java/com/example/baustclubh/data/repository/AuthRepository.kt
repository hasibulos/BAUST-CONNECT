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

            // Firestore থেকে স্টুডেন্ট আইডি দিয়ে ডকুমেন্ট খোঁজা
            val snapshot = db.collection("users").document(studentId).get().await()

            if (snapshot.exists()) {
                val user = snapshot.toObject(User::class.java)
                Log.d("AuthRepo", "User found: ${user?.name}")
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

    /**
     * ইমেলটি আগে থেকে রেজিস্ট্রেশন করা আছে কিনা তা চেক করার ফাংশন
     */
    suspend fun isEmailAlreadyRegistered(email: String): Boolean {
        return try {
            // Firestore কুয়েরি: 'email' ফিল্ডটি চেক করবে
            val result = db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .await()

            !result.isEmpty // যদি রেজাল্ট খালি না হয়, তার মানে ইমেলটি আছে
        } catch (e: Exception) {
            Log.e("AuthRepo", "Email check error: ${e.message}")
            false
        }
    }

    suspend fun registerUser(user: User): Boolean {
        return try {
            // ১. আইডি দিয়ে চেক করা (যাতে একই আইডিতে দুই ইউজার না হয়)
            val existing = db.collection("users").document(user.studentId).get().await()
            if (existing.exists()) {
                Log.d("AuthRepo", "Registration failed: Student ID already exists")
                false
            } else {
                // ২. ইমেল দিয়ে চেক করার লজিক (ViewModel থেকে কল করা হবে)
                db.collection("users").document(user.studentId).set(user).await()
                Log.d("AuthRepo", "Registration success for: ${user.name}")
                true
            }
        } catch (e: Exception) {
            Log.e("AuthRepo", "Registration error: ${e.message}")
            false
        }
    }
}