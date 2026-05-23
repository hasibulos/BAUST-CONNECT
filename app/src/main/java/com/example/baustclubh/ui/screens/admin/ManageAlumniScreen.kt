package com.example.baustclubh.ui.screens.admin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage // 👈 ছবি লোড করার জন্য ইম্পোর্ট
import com.example.baustclubh.ui.screens.alumni.Alumni
import com.example.baustclubh.ui.theme.*
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageAlumniScreen(navController: NavController) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    // ডাটাবেজ থেকে আসা লিস্ট স্টেট
    var alumniList by remember { mutableStateOf<List<Alumni>>(listOf()) }
    var isLoading by remember { mutableStateOf(true) }

    // ফর্ম স্ক্রল করার জন্য স্টেট
    val formScrollState = rememberScrollState()

    // ➕ নতুন অ্যালমনাই অ্যাড করার ইনপুট স্টেটস (Dept ও Batch আলাদা করা হয়েছে)
    var newName by remember { mutableStateOf("") }
    var newDept by remember { mutableStateOf("") }  // 👈 নতুন আলাদা ডিপার্টমেন্ট ফিল্ড
    var newBatchNum by remember { mutableStateOf("") } // 👈 নতুন আলাদা ব্যাচ নম্বর ফিল্ড
    var newPosition by remember { mutableStateOf("") }
    var newIcon by remember { mutableStateOf("") }
    var newEmail by remember { mutableStateOf("") }
    var newLinkedin by remember { mutableStateOf("") }
    var newFacebook by remember { mutableStateOf("") }
    var newBio by remember { mutableStateOf("") }

    // 📝 এডিট ডায়ালগ স্টেটস
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedAlumni by remember { mutableStateOf<Alumni?>(null) }
    var editName by remember { mutableStateOf("") }
    var editDept by remember { mutableStateOf("") }  // 👈 এডিট ডায়ালগের জন্য আলাদা ডিপার্টমেন্ট
    var editBatchNum by remember { mutableStateOf("") } // 👈 এডিট ডায়ালগের জন্য আলাদা ব্যাচ
    var editPosition by remember { mutableStateOf("") }
    var editIcon by remember { mutableStateOf("") }
    var editEmail by remember { mutableStateOf("") }
    var editLinkedin by remember { mutableStateOf("") }
    var editFacebook by remember { mutableStateOf("") }
    var editBio by remember { mutableStateOf("") }

    // 🔄 ফায়ারস্টোর থেকে রিয়েলটাইম ডাটা লোড
    LaunchedEffect(Unit) {
        db.collection("alumni").addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                alumniList = snapshot.toObjects(Alumni::class.java)
            }
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Alumni Network", color = MaterialTheme.colorScheme.onBackground, fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Text("←", color = MaterialTheme.colorScheme.onBackground, fontSize = 24.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            // ================= ➕ ১. ADD ALUMNI FORM SECTION (Scrollable) =================
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 410.dp) // স্ক্রিন সাইজ ও নতুন ফিল্ড অ্যাডজাস্ট করার জন্য হাইট সামান্য বাড়ানো হয়েছে
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(formScrollState),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Add New Alumni Profile",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    OutlinedTextField(
                        value = newName, onValueChange = { newName = it },
                        label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth()
                    )

                    // 👥 ডিপার্টমেন্ট ও ব্যাচের ইনপুট ফিল্ড দুটিকে আলাদা রো (Row) তে পাশাপাশি সুন্দরভাবে সাজানো হয়েছে
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = newDept, onValueChange = { newDept = it },
                            label = { Text("Department (e.g., CSE)") }, modifier = Modifier.weight(1f),
                            placeholder = { Text("CSE") }
                        )
                        OutlinedTextField(
                            value = newBatchNum, onValueChange = { newBatchNum = it },
                            label = { Text("Batch (e.g., 18)") }, modifier = Modifier.weight(1f),
                            placeholder = { Text("18") }
                        )
                    }

                    OutlinedTextField(
                        value = newIcon, onValueChange = { newIcon = it },
                        label = { Text("Image URL") }, modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("https://...") }
                    )

                    OutlinedTextField(
                        value = newPosition, onValueChange = { newPosition = it },
                        label = { Text("Current Position & Company") }, modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newEmail, onValueChange = { newEmail = it },
                        label = { Text("Professional Email") }, modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newLinkedin, onValueChange = { newLinkedin = it },
                        label = { Text("LinkedIn Profile Link") }, modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newFacebook, onValueChange = { newFacebook = it },
                        label = { Text("Facebook Profile Link") }, modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newBio, onValueChange = { newBio = it },
                        label = { Text("Short Bio / About") }, modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )

                    Button(
                        onClick = {
                            if (newName.isNotBlank() && newDept.isNotBlank() && newBatchNum.isNotBlank() && newPosition.isNotBlank()) {
                                val docRef = db.collection("alumni").document()

                                // ফিল্টার সিস্টেম ঠিক রাখার জন্য ডিপার্টমেন্ট ও ব্যাচকে একসাথে মার্জ করে "batch" ভেরিয়েবলে পাঠানো হচ্ছে
                                val formattedBatch = "${newDept.trim().uppercase()} ${newBatchNum.trim()}"

                                val alumniData = Alumni(
                                    id = docRef.id,
                                    name = newName,
                                    batch = formattedBatch, // আপনার স্ক্রিন ও ফিল্টারের সাথে সিঙ্কড
                                    position = newPosition,
                                    icon = newIcon.ifBlank { "" },
                                    email = newEmail,
                                    linkedin = newLinkedin,
                                    facebook = newFacebook,
                                    bio = newBio.ifBlank { "BAUST Alumnus | Proud Graduate" }
                                )

                                docRef.set(alumniData)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Alumni added successfully!", Toast.LENGTH_SHORT).show()
                                        newName = ""
                                        newDept = ""
                                        newBatchNum = ""
                                        newPosition = ""
                                        newIcon = ""
                                        newEmail = ""
                                        newLinkedin = ""
                                        newFacebook = ""
                                        newBio = ""
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(context, "Failed to add alumni", Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                Toast.makeText(context, "Please fill Name, Department, Batch & Position", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.PersonAdd, contentDescription = "Add")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add Alumni Member", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Existing Alumni List",
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // ================= 📜 ২. LIST & MANAGEMENT SECTION =================
            Box(modifier = Modifier.fillMaxSize().weight(1f)) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.primary)
                } else if (alumniList.isEmpty()) {
                    Text(text = "No Alumni found to manage.", color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(alumniList) { alumni ->
                            AdminAlumniCard(
                                alumni = alumni,
                                onEditClick = {
                                    selectedAlumni = alumni
                                    editName = alumni.name

                                    // এডিট বক্সে দেখানোর সুবিধার্থে "CSE 18" বা "CSE Batch 18" থেকে স্প্লিট করে ডাটা আলাদা করা হচ্ছে
                                    val batchParts = alumni.batch.split(" ")
                                    editDept = batchParts.firstOrNull() ?: ""
                                    editBatchNum = batchParts.lastOrNull() ?: ""

                                    editPosition = alumni.position
                                    editIcon = alumni.icon
                                    editEmail = alumni.email
                                    editLinkedin = alumni.linkedin
                                    editFacebook = alumni.facebook
                                    editBio = alumni.bio
                                    showEditDialog = true
                                },
                                onDeleteClick = {
                                    db.collection("alumni").document(alumni.id).delete()
                                        .addOnSuccessListener {
                                            Toast.makeText(context, "Alumni deleted successfully", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    // ================= 📝 ৩. EDIT DIALOG POP-UP (আলাদা আলাদা ফিল্ড সহ) =================
    if (showEditDialog && selectedAlumni != null) {
        val dialogScrollState = rememberScrollState()

        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            containerColor = MaterialTheme.colorScheme.surface,
            title = { Text("Edit Alumni Info", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(dialogScrollState),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(value = editName, onValueChange = { editName = it }, label = { Text("Name") })

                    // এডিটের সময়ও ডিপার্টমেন্ট এবং ব্যাচের ফিল্ড দুটিকে আলাদা ইনপুট ফিল্ড হিসেবে রাখা হয়েছে
                    OutlinedTextField(value = editDept, onValueChange = { editDept = it }, label = { Text("Department (e.g., CSE)") })
                    OutlinedTextField(value = editBatchNum, onValueChange = { editBatchNum = it }, label = { Text("Batch Number (e.g., 18)") })

                    OutlinedTextField(value = editIcon, onValueChange = { editIcon = it }, label = { Text("Avatar Image URL") })
                    OutlinedTextField(value = editPosition, onValueChange = { editPosition = it }, label = { Text("Position/Company") })
                    OutlinedTextField(value = editEmail, onValueChange = { editEmail = it }, label = { Text("Email Address") })
                    OutlinedTextField(value = editLinkedin, onValueChange = { editLinkedin = it }, label = { Text("LinkedIn URL") })
                    OutlinedTextField(value = editFacebook, onValueChange = { editFacebook = it }, label = { Text("Facebook URL") })
                    OutlinedTextField(value = editBio, onValueChange = { editBio = it }, label = { Text("Bio / Summary") })
                }
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    onClick = {
                        // এডিট সেভ করার সময়েও টেক্সট দুটিকে কম্বাইন করে ফরম্যাট সিঙ্ক রাখা হচ্ছে
                        val updatedBatchString = "${editDept.trim().uppercase()} ${editBatchNum.trim()}"

                        val updatedData = mapOf(
                            "name" to editName,
                            "batch" to updatedBatchString,
                            "position" to editPosition,
                            "icon" to editIcon,
                            "email" to editEmail,
                            "linkedin" to editLinkedin,
                            "facebook" to editFacebook,
                            "bio" to editBio
                        )
                        db.collection("alumni").document(selectedAlumni!!.id).update(updatedData)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Updated successfully!", Toast.LENGTH_SHORT).show()
                                showEditDialog = false
                            }
                    }
                ) {
                    Text("Update")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        )
    }
}

@Composable
fun AdminAlumniCard(
    alumni: Alumni,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (alumni.icon.startsWith("http")) {
                    AsyncImage(
                        model = alumni.icon,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = alumni.name.take(1).uppercase(),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Column {
                    Text(text = alumni.name, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = alumni.batch, color = MaterialTheme.colorScheme.primary, fontSize = 13.sp)
                    Text(text = alumni.position, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(onClick = onEditClick) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}