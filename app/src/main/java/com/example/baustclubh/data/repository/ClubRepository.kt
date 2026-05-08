package com.example.baustclubh.data.repository

// সঠিক ডাটা ক্লাসটি ইম্পোর্ট করতে হবে।
// যদি Club ক্লাসটি ManageClubsScreen ফাইলের ভেতর থাকে তবে নিচের ইম্পোর্টটি চেক করুন
import com.example.baustclubh.ui.screens.admin.Club
import com.google.firebase.firestore.FirebaseFirestore

class ClubRepository {

    private val firestore = FirebaseFirestore.getInstance()

    /**
     * নতুন ক্লাব যোগ করার ফাংশন
     */
    fun addClub(club: Club, onResult: (Boolean, String) -> Unit) {
        // ভ্যালিডেশন চেক
        if (club.name.isBlank()) {
            onResult(false, "Club name is required")
            return
        }

        // ফায়ারস্টোরে অটো-আইডি জেনারেট করা
        val docRef = firestore.collection("clubs").document()
        val finalClub = club.copy(id = docRef.id)

        docRef.set(finalClub)
            .addOnSuccessListener {
                onResult(true, "Club added successfully")
            }
            .addOnFailureListener { e ->
                onResult(false, e.message ?: "Failed to add club")
            }
    }

    /**
     * রিয়েল-টাইম ক্লাব লিস্ট পাওয়ার ফাংশন
     */
    fun getClubList(onResult: (List<Club>) -> Unit) {
        firestore.collection("clubs")
            .get()
            .addOnSuccessListener { result ->
                val list = result.documents.mapNotNull { document ->
                    document.toObject(Club::class.java)
                }
                onResult(list)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }

    /**
     * ক্লাবের মেম্বার সংখ্যা ট্রানজেকশনের মাধ্যমে বাড়ানো
     */
    fun increaseMemberCount(clubId: String, onResult: (Boolean, String) -> Unit) {
        val docRef = firestore.collection("clubs").document(clubId)

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(docRef)
            val currentCount = snapshot.getLong("totalMembers") ?: 0

            transaction.update(docRef, "totalMembers", currentCount + 1)
        }.addOnSuccessListener {
            onResult(true, "Member count increased")
        }.addOnFailureListener { e ->
            onResult(false, e.message ?: "Failed to update member count")
        }
    }

    /**
     * ক্লাব আপডেট করার ফাংশন
     */
    fun updateClub(club: Club, onResult: (Boolean, String) -> Unit) {
        if (club.id.isBlank()) {
            onResult(false, "Invalid club id")
            return
        }

        val docRef = firestore.collection("clubs").document(club.id)
        docRef.set(club)
            .addOnSuccessListener {
                onResult(true, "Club updated successfully")
            }
            .addOnFailureListener { e ->
                onResult(false, e.message ?: "Failed to update club")
            }
    }

    /**
     * ক্লাব ডিলিট করার ফাংশন
     */
    fun deleteClub(clubId: String, onResult: (Boolean, String) -> Unit) {
        if (clubId.isBlank()) {
            onResult(false, "Invalid club id")
            return
        }

        firestore.collection("applications")
            .whereEqualTo("clubId", clubId)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    onResult(false, "Cannot delete club with pending applications")
                } else {
                    firestore.collection("clubs").document(clubId)
                        .delete()
                        .addOnSuccessListener {
                            onResult(true, "Club deleted successfully")
                        }
                        .addOnFailureListener { e ->
                            onResult(false, e.message ?: "Failed to delete club")
                        }
                }
            }
            .addOnFailureListener { e ->
                onResult(false, e.message ?: "Failed to check related data")
            }
    }
}