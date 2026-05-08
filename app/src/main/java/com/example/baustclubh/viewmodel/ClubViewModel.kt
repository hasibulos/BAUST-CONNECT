package com.example.baustclubh.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baustclubh.ui.screens.admin.Club
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClubViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _clubs = MutableStateFlow<List<Club>>(emptyList())
    val clubs: StateFlow<List<Club>> = _clubs

    init {
        fetchClubs()
    }

    // --- ১. ফায়ারবেস থেকে রিয়েল-টাইম ডাটা ফেচ করা ---
    private fun fetchClubs() {
        db.collection("clubs").addSnapshotListener { snapshot, error ->
            if (error != null) return@addSnapshotListener
            if (snapshot != null) {
                _clubs.value = snapshot.toObjects(Club::class.java)
            }
        }
    }

    // --- ২. নতুন ক্লাব অ্যাড করা ---
    fun addClub(name: String, moderator: String, website: String, dept: String, type: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val id = db.collection("clubs").document().id
            val newClub = Club(id, name, moderator, website, dept, type)

            db.collection("clubs").document(id).set(newClub)
                .addOnSuccessListener { onComplete(true) }
                .addOnFailureListener { onComplete(false) }
        }
    }

    // --- ৩. ক্লাব ডিলিট করা ---
    fun deleteClub(clubId: String, onComplete: (Boolean) -> Unit) {
        db.collection("clubs").document(clubId).delete()
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }
}