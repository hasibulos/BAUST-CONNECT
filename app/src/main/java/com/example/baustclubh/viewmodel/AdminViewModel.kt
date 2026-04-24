package com.example.baustclubh.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class ApplicationData(
    val id: String = "",
    val studentId: String = "",
    val studentName: String = "",
    val clubName: String = "",
    val reason: String = "",
    val status: String = ""
)

sealed class ApplicationsState {
    object Loading : ApplicationsState()
    data class Success(val applications: List<ApplicationData>) : ApplicationsState()
    data class Error(val message: String) : ApplicationsState()
}

class AdminViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _applicationsState = MutableStateFlow<ApplicationsState>(ApplicationsState.Loading)
    val applicationsState: StateFlow<ApplicationsState> = _applicationsState.asStateFlow()

    fun getPendingApplications() {
        viewModelScope.launch {
            _applicationsState.value = ApplicationsState.Loading
            try {
                val snapshot = db.collection("applications")
                    .whereEqualTo("status", "pending")
                    .get()
                    .await()

                val applications = snapshot.documents.mapNotNull { document ->
                    val data = document.data
                    if (data != null) {
                        ApplicationData(
                            id = document.id,
                            studentId = data["studentId"] as? String ?: "",
                            studentName = data["studentName"] as? String ?: "",
                            clubName = data["clubName"] as? String ?: "",
                            reason = data["reason"] as? String ?: "",
                            status = data["status"] as? String ?: ""
                        )
                    } else {
                        null
                    }
                }
                _applicationsState.value = ApplicationsState.Success(applications)
            } catch (e: Exception) {
                _applicationsState.value = ApplicationsState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun updateApplicationStatus(applicationId: String, status: String) {
        viewModelScope.launch {
            try {
                db.collection("applications").document(applicationId)
                    .update("status", status)
                    .await()
                getPendingApplications() // Refresh the list
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}