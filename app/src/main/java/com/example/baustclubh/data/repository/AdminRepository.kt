package com.example.baustclubh.data.repository

import android.content.Context
import com.example.baustclubh.data.model.User
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdminRepository(private val context: Context) {
    private val db = FirebaseFirestore.getInstance()

    fun createAdminAccount(
        adminUser: User,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        try {
            // আপনার প্রোজেক্টের google-services.json ফাইল থেকে নিখুঁত কনফিগারেশন রিড করা
            val mainAppOptions = FirebaseApp.getInstance().options

            val options = FirebaseOptions.Builder()
                .setApiKey(mainAppOptions.apiKey)
                .setApplicationId(mainAppOptions.applicationId)
                .setProjectId(mainAppOptions.projectId ?: "")
                .setDatabaseUrl(mainAppOptions.databaseUrl ?: "")
                .setStorageBucket(mainAppOptions.storageBucket ?: "")
                .build()

            // একটি সম্পূর্ণ আইসোলেটেড কন্টেইনার তৈরি করা যাতে সুপার অ্যাডমিনের কারেন্ট লগইন সেশন বিন্দুমাত্র প্রভাবিত না হয়
            val adminCreationApp = try {
                FirebaseApp.initializeApp(context, options, "AdminCreationApp")
            } catch (e: Exception) {
                FirebaseApp.getInstance("AdminCreationApp")
            }

            val secondaryAuth = FirebaseAuth.getInstance(adminCreationApp)

            // আপনি যে ইমেইল এবং পাসওয়ার্ড ইনপুট দিয়েছেন, সেটা দিয়েই ফায়ারবেস অথেন্টিকেশনে অ্যাকাউন্ট তৈরি হচ্ছে
            secondaryAuth.createUserWithEmailAndPassword(adminUser.email, password)
                .addOnSuccessListener { authResult ->
                    val uid = authResult.user?.uid ?: ""

                    // ফায়ারস্টোরে সেভ করার জন্য অবজেক্ট রেডি করা
                    val finalAdminData = adminUser.copy(studentId = uid)

                    // ফায়ারস্টোরের 'users' কালেকশনে রোল এবং ডিপার্টমেন্ট সহ ডাটা পুশ
                    db.collection("users").document(uid).set(finalAdminData)
                        .addOnSuccessListener {
                            // নতুন অ্যাডমিনের সাময়িক সেশনটি রিলিজ করে দেওয়া
                            secondaryAuth.signOut()
                            onResult(true, "Admin Account Successfully Created!")
                        }
                        .addOnFailureListener {
                            onResult(false, "Firestore Database Error: ${it.message}")
                        }
                }
                .addOnFailureListener { e ->
                    onResult(false, "Firebase Auth Error: ${e.message}")
                }
        } catch (e: Exception) {
            onResult(false, "Initialization Error: ${e.message}")
        }
    }
}