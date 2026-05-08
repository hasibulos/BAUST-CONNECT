package com.example.baustclubh.ui.screens.admin

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baustclubh.data.model.Event
import com.example.baustclubh.ui.theme.*
import com.example.baustclubh.viewmodel.AuthViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageEventsScreen(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()

    // Form States
    var eventTitle by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf("") }
    var eventTime by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var organizingClub by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    var isUploading by remember { mutableStateOf(false) }
    var isEditMode by remember { mutableStateOf(false) }
    var editingEventId by remember { mutableStateOf("") }
    var currentImageUrl by remember { mutableStateOf("") }
    var currentImageName by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    var eventList by remember { mutableStateOf<List<Event>>(listOf()) }

    // Real-time Event Data Load
    LaunchedEffect(Unit) {
        db.collection("events").addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                eventList = snapshot.toObjects(Event::class.java)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Events", color = TextWhite, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundDark),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Text("←", color = TextWhite, fontSize = 24.sp)
                    }
                }
            )
        },
        containerColor = BackgroundDark
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(text = if (isEditMode) "Edit Event" else "Create New Event", color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Card(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        AdminInputField(value = eventTitle, onValueChange = { eventTitle = it }, label = "Event Title")

                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            placeholder = { Text("Event Details/Description", color = TextGray) },
                            modifier = Modifier.fillMaxWidth().height(100.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = PrimaryBlue),
                            shape = RoundedCornerShape(8.dp)
                        )

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(modifier = Modifier.weight(1f)) { AdminInputField(value = eventDate, onValueChange = { eventDate = it }, label = "Date (e.g. 12 May)") }
                            Box(modifier = Modifier.weight(1f)) { AdminInputField(value = eventTime, onValueChange = { eventTime = it }, label = "Time (e.g. 10:00 AM)") }
                        }

                        AdminInputField(value = location, onValueChange = { location = it }, label = "Venue/Location")
                        AdminInputField(value = organizingClub, onValueChange = { organizingClub = it }, label = "Organizing Club Name")

                        OutlinedButton(
                            onClick = { launcher.launch("image/*") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite)
                        ) {
                            Icon(Icons.Default.Image, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text(if (imageUri != null) "Image Selected" else "Select Event Banner")
                        }

                        Button(
                            onClick = {
                                if (eventTitle.isNotEmpty()) {
                                    isUploading = true
                                    val finalId = if (isEditMode) editingEventId else db.collection("events").document().id

                                    if (imageUri != null) {
                                        val fileName = "event_images/${UUID.randomUUID()}.jpg"
                                        val storageRef = storage.reference.child(fileName)

                                        storageRef.putFile(imageUri!!)
                                            .continueWithTask { task ->
                                                if (!task.isSuccessful) {
                                                    task.exception?.let { throw it }
                                                }
                                                storageRef.downloadUrl
                                            }
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    val downloadUrl = task.result.toString()
                                                val eventData = Event(
                                                    id = finalId,
                                                    title = eventTitle,
                                                    description = description,
                                                    date = eventDate,
                                                    time = eventTime,
                                                    location = location,
                                                    clubName = organizingClub,
                                                    imageUrl = downloadUrl,
                                                    imageName = fileName
                                                )

                                                    db.collection("events").document(finalId).set(eventData)
                                                        .addOnSuccessListener {
                                                            isUploading = false; isEditMode = false; imageUri = null
                                                            eventTitle = ""; description = ""; eventDate = ""; eventTime = ""; location = ""; organizingClub = ""
                                                            Toast.makeText(context, "Event Saved!", Toast.LENGTH_SHORT).show()
                                                        }
                                                        .addOnFailureListener {
                                                            isUploading = false
                                                            Toast.makeText(context, "Firestore Error: ${it.message}", Toast.LENGTH_SHORT).show()
                                                        }
                                                } else {
                                                    isUploading = false
                                                    Toast.makeText(context, "Upload Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                    } else {
                                        val eventData = Event(finalId, eventTitle, description, eventDate, eventTime, location, "", organizingClub, currentImageUrl, currentImageName)
                                        db.collection("events").document(finalId).set(eventData)
                                            .addOnSuccessListener {
                                                isUploading = false; isEditMode = false
                                                Toast.makeText(context, "Event Updated!", Toast.LENGTH_SHORT).show()
                                            }
                                            .addOnFailureListener {
                                                isUploading = false
                                                Toast.makeText(context, "Update Failed: ${it.message}", Toast.LENGTH_SHORT).show()
                                            }
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isUploading,
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                        ) {
                            if (isUploading) CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
                            else Text(if (isEditMode) "Save Event" else "Post Event")
                        }
                    }
                }
            }

            // Event List Table
            item {
                Row(modifier = Modifier.fillMaxWidth().background(PrimaryBlue).padding(10.dp)) {
                    Text("Event Title", modifier = Modifier.weight(1.5f), color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Action", modifier = Modifier.weight(1f), color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }

            items(eventList) { event ->
                Row(
                    modifier = Modifier.fillMaxWidth().background(CardBackground).padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(event.title, modifier = Modifier.weight(1.5f), color = TextWhite, fontSize = 13.sp)
                    Row(Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                        Text("Edit", color = Color.Green, modifier = Modifier.clickable {
                            isEditMode = true
                            editingEventId = event.id
                            eventTitle = event.title
                            description = event.description
                            eventDate = event.date
                            eventTime = event.time
                            location = event.location
                            organizingClub = event.clubName
                            currentImageUrl = event.imageUrl
                            currentImageName = event.imageName
                        })
                        Text("Delete", color = Color.Red, modifier = Modifier.clickable {
                            db.collection("events").document(event.id).delete().addOnSuccessListener {
                                if (event.imageName.isNotEmpty()) {
                                    storage.reference.child(event.imageName).delete()
                                }
                                Toast.makeText(context, "Event Removed", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }
                HorizontalDivider(color = Color.DarkGray, thickness = 0.5.dp)
            }
        }
    }
}
