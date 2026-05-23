package com.example.baustclubh.ui.screens.admin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageEventsScreen(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    // Form States
    var eventTitle by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf("") }
    var eventTime by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var organizingClub by remember { mutableStateOf("") }
    var manualImageUrl by remember { mutableStateOf("") }

    // Category & Type States
    var selectedCategory by remember { mutableStateOf("Tech") }
    var isCategoryExpanded by remember { mutableStateOf(false) }
    val categories = listOf("Tech", "Culture", "Sport", "Workshop")

    var selectedType by remember { mutableStateOf("Seminar") }
    var isTypeExpanded by remember { mutableStateOf(false) }
    val eventTypes = listOf("Seminar", "Contest", "Fest", "Webinar")

    // 🆕 Department States (নতুন যুক্ত করা হয়েছে)
    var selectedDept by remember { mutableStateOf("CSE") }
    var isDeptExpanded by remember { mutableStateOf(false) }
    val departments = listOf("CSE", "EEE", "ME", "IPE", "CE", "BBA", "English", "All Dept")

    var isSaving by remember { mutableStateOf(false) }
    var isEditMode by remember { mutableStateOf(false) }
    var editingEventId by remember { mutableStateOf("") }

    var eventList by remember { mutableStateOf<List<Event>>(listOf()) }

    // ডাটা রিয়েলটাইম লোড করা
    LaunchedEffect(Unit) {
        db.collection("events").addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                eventList = snapshot.toObjects(Event::class.java)
            }
        }
    }

    fun resetEventForm() {
        isEditMode = false
        editingEventId = ""
        eventTitle = ""
        description = ""
        eventDate = ""
        eventTime = ""
        location = ""
        organizingClub = ""
        manualImageUrl = ""
        selectedCategory = "Tech"
        selectedType = "Seminar"
        selectedDept = "CSE" // ডিপার্টমেন্ট রিসেট
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Events", color = TextWhite, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundDark),
                navigationIcon = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("← Back", color = PrimaryBlue, fontWeight = FontWeight.Bold, fontSize = 16.sp)
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
                            Box(modifier = Modifier.weight(1f)) { AdminInputField(value = eventDate, onValueChange = { eventDate = it }, label = "Date") }
                            Box(modifier = Modifier.weight(1f)) { AdminInputField(value = eventTime, onValueChange = { eventTime = it }, label = "Time") }
                        }

                        AdminInputField(value = location, onValueChange = { location = it }, label = "Venue/Location")
                        AdminInputField(value = organizingClub, onValueChange = { organizingClub = it }, label = "Organizing Club Name")

                        AdminInputField(value = manualImageUrl, onValueChange = { manualImageUrl = it }, label = "Event Banner URL (https://...)")

                        // ================= 🏷️ CATEGORY DROPDOWN =================
                        Box(modifier = Modifier.fillMaxWidth()) {
                            ExposedDropdownMenuBox(
                                expanded = isCategoryExpanded,
                                onExpandedChange = { isCategoryExpanded = !isCategoryExpanded }
                            ) {
                                OutlinedTextField(
                                    value = selectedCategory,
                                    onValueChange = {},
                                    readOnly = true,
                                    placeholder = { Text("Select Event Category", color = TextGray) },
                                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, null, tint = PrimaryBlue) },
                                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = PrimaryBlue, unfocusedBorderColor = Color.DarkGray)
                                )
                                ExposedDropdownMenu(
                                    expanded = isCategoryExpanded,
                                    onDismissRequest = { isCategoryExpanded = false },
                                    modifier = Modifier.background(CardBackground)
                                ) {
                                    categories.forEach { cat ->
                                        DropdownMenuItem(
                                            text = { Text(cat, color = TextWhite) },
                                            onClick = {
                                                selectedCategory = cat
                                                isCategoryExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        // ================= 🔽 EVENT TYPE DROPDOWN =================
                        Box(modifier = Modifier.fillMaxWidth()) {
                            ExposedDropdownMenuBox(
                                expanded = isTypeExpanded,
                                onExpandedChange = { isTypeExpanded = !isTypeExpanded }
                            ) {
                                OutlinedTextField(
                                    value = selectedType,
                                    onValueChange = {},
                                    readOnly = true,
                                    placeholder = { Text("Select Event Type", color = TextGray) },
                                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, null, tint = PrimaryBlue) },
                                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = PrimaryBlue, unfocusedBorderColor = Color.DarkGray)
                                )
                                ExposedDropdownMenu(
                                    expanded = isTypeExpanded,
                                    onDismissRequest = { isTypeExpanded = false },
                                    modifier = Modifier.background(CardBackground)
                                ) {
                                    eventTypes.forEach { type ->
                                        DropdownMenuItem(
                                            text = { Text(type, color = TextWhite) },
                                            onClick = {
                                                selectedType = type
                                                isTypeExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        // ================= 🏢 🆕 DEPARTMENT DROPDOWN (নতুন যুক্ত করা হয়েছে) =================
                        Box(modifier = Modifier.fillMaxWidth()) {
                            ExposedDropdownMenuBox(
                                expanded = isDeptExpanded,
                                onExpandedChange = { isDeptExpanded = !isDeptExpanded }
                            ) {
                                OutlinedTextField(
                                    value = selectedDept,
                                    onValueChange = {},
                                    readOnly = true,
                                    placeholder = { Text("Select Department", color = TextGray) },
                                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, null, tint = PrimaryBlue) },
                                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = PrimaryBlue, unfocusedBorderColor = Color.DarkGray)
                                )
                                ExposedDropdownMenu(
                                    expanded = isDeptExpanded,
                                    onDismissRequest = { isDeptExpanded = false },
                                    modifier = Modifier.background(CardBackground)
                                ) {
                                    departments.forEach { dept ->
                                        DropdownMenuItem(
                                            text = { Text(dept, color = TextWhite) },
                                            onClick = {
                                                selectedDept = dept
                                                isDeptExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        // --- সরাসরি Firestore-এ সেভ লজিক (আপডেটেড) ---
                        Button(
                            onClick = {
                                if (eventTitle.isNotEmpty()) {
                                    isSaving = true
                                    val finalId = if (isEditMode) editingEventId else db.collection("events").document().id

                                    val eventData = Event(
                                        id = finalId,
                                        title = eventTitle,
                                        description = description,
                                        date = eventDate,
                                        time = eventTime,
                                        location = location,
                                        clubName = organizingClub,
                                        imageUrl = manualImageUrl.trim(),
                                        imageName = "",
                                        category = selectedCategory,
                                        type = selectedType,
                                        department = selectedDept // ⚡ ডিপার্টমেন্ট ডেটাবেজে যুক্ত হলো
                                    )

                                    db.collection("events").document(finalId).set(eventData)
                                        .addOnSuccessListener {
                                            isSaving = false
                                            resetEventForm()
                                            Toast.makeText(context, "Event Saved Successfully!", Toast.LENGTH_SHORT).show()
                                        }
                                        .addOnFailureListener { e ->
                                            isSaving = false
                                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isSaving,
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                        ) {
                            if (isSaving) CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                            else Text(if (isEditMode) "Update Event" else "Post Event", color = TextWhite, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // ইভেন্ট লিস্ট টেবিল
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
                            manualImageUrl = event.imageUrl
                            selectedCategory = if (event.category.isNotEmpty()) event.category else "Tech"
                            selectedType = if (event.type.isNotEmpty()) event.type else "Seminar"
                            selectedDept = if (event.department.isNotEmpty()) event.department else "CSE" // ⚡ এডিটের সময় ডিপার্টমেন্ট লোড
                        })
                        Text("Delete", color = Color.Red, modifier = Modifier.clickable {
                            db.collection("events").document(event.id).delete().addOnSuccessListener {
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