package com.example.baustclubh.data.repository

import com.example.baustclubh.data.model.Event
import com.google.firebase.firestore.FirebaseFirestore

class EventRepository {

    private val firestore = FirebaseFirestore.getInstance()

    /**
     * নতুন ইভেন্ট যোগ করার ফাংশন
     */
    fun addEvent(event: Event, onResult: (Boolean, String) -> Unit) {
        // ভ্যালিডেশন চেক
        if (event.title.isBlank()) {
            onResult(false, "Event title is required")
            return
        }
        if (event.date.isBlank() || event.time.isBlank()) {
            onResult(false, "Date and Time are required")
            return
        }
        if (event.imageUrl.isBlank()) {
            onResult(false, "Event image is required")
            return
        }

        // ফায়ারস্টোর আইডি জেনারেট করা
        val docRef = firestore.collection("events").document()
        val finalEvent = event.copy(id = docRef.id)

        docRef.set(finalEvent)
            .addOnSuccessListener {
                onResult(true, "Event posted successfully")
            }
            .addOnFailureListener { e ->
                onResult(false, e.message ?: "Failed to post event")
            }
    }

    /**
     * সব ইভেন্টের লিস্ট সংগ্রহের ফাংশন
     */
    fun getEventList(onResult: (List<Event>) -> Unit) {
        firestore.collection("events")
            .orderBy("date") // তারিখ অনুযায়ী সাজানোর জন্য
            .get()
            .addOnSuccessListener { result ->
                val list = result.documents.mapNotNull { document ->
                    document.toObject(Event::class.java)
                }
                onResult(list)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }

    /**
     * ইভেন্ট আপডেট করার ফাংশন
     */
    fun updateEvent(event: Event, onResult: (Boolean, String) -> Unit) {
        if (event.id.isBlank()) {
            onResult(false, "Invalid event id")
            return
        }

        val docRef = firestore.collection("events").document(event.id)

        docRef.set(event)
            .addOnSuccessListener {
                onResult(true, "Event updated successfully")
            }
            .addOnFailureListener { e ->
                onResult(false, e.message ?: "Failed to update event")
            }
    }

    /**
     * ইভেন্ট ডিলিট করার ফাংশন
     */
    fun deleteEvent(eventId: String, onResult: (Boolean, String) -> Unit) {
        if (eventId.isBlank()) {
            onResult(false, "Invalid event id")
            return
        }

        // চেক করা হচ্ছে এই ইভেন্টের সাথে কোনো অ্যাটেনডেন্স ডাটা আছে কি না
        firestore.collection("attendance")
            .whereEqualTo("eventId", eventId)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    // যদি ইভেন্টে অলরেডি কেউ অ্যাটেনডেন্স দিয়ে থাকে, তবে সতর্ক করা
                    onResult(false, "Cannot delete event with existing attendance records")
                } else {
                    firestore.collection("events").document(eventId)
                        .delete()
                        .addOnSuccessListener {
                            onResult(true, "Event deleted successfully")
                        }
                        .addOnFailureListener { e ->
                            onResult(false, e.message ?: "Failed to delete event")
                        }
                }
            }
            .addOnFailureListener { e ->
                onResult(false, e.message ?: "Error checking attendance records")
            }
    }
}