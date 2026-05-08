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
import androidx.compose.material.icons.filled.ArrowDropDown
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
import com.example.baustclubh.ui.theme.*
import com.example.baustclubh.viewmodel.AuthViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

// ১. ডাটা মডেল
data class Club(
    val id: String = "",
    val name: String = "",
    val moderator: String = "",
    val email: String = "",
    val website: String = "",
    val department: String = "",
    val type: String = "",
    val imageUrl: String = "",
    val imageName: String = "",
    val description: String = "",
    val establishedDate: String = "",
    val totalMembers: Int = 0,
    val memberList: List<String> = emptyList(),
    val eventList: List<String> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageClubsScreen(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()

    var clubName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var establishedDate by remember { mutableStateOf("") }
    var moderatorName by remember { mutableStateOf("") }
    var clubEmail by remember { mutableStateOf("") }
    var websiteUrl by remember { mutableStateOf("") }
    var selectedDept by remember { mutableStateOf("Select Department") }
    var selectedType by remember { mutableStateOf("Select Type") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    var isUploading by remember { mutableStateOf(false) }
    var isEditMode by remember { mutableStateOf(false) }
    var editingClubId by remember { mutableStateOf("") }
    var currentImageUrl by remember { mutableStateOf("") }
    var currentImageName by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    var deptExpanded by remember { mutableStateOf(false) }
    var typeExpanded by remember { mutableStateOf(false) }

    val departments = listOf("CSE", "EEE", "ME", "IPE", "CE", "BBA", "English")
    val types = listOf("Technical", "Cultural", "Sports", "Social")

    var clubList by remember { mutableStateOf<List<Club>>(listOf()) }

    LaunchedEffect(Unit) {
        db.collection("clubs").addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                clubList = snapshot.toObjects(Club::class.java).filter { it.name.isNotEmpty() }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Clubs", color = TextWhite, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundDark),
                navigationIcon = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("← Back", color = PrimaryBlue)
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
                Text(
                    text = if (isEditMode) "Edit Club Details" else "Add Club",
                    color = TextWhite, fontSize = 22.sp, fontWeight = FontWeight.Bold
                )
                Card(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        AdminInputField(value = clubName, onValueChange = { clubName = it }, label = "Club Name")

                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            placeholder = { Text("Club Description...", color = TextGray) },
                            modifier = Modifier.fillMaxWidth().height(100.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = PrimaryBlue),
                            shape = RoundedCornerShape(4.dp)
                        )

                        AdminInputField(value = establishedDate, onValueChange = { establishedDate = it }, label = "Established Date (e.g. 2024)")
                        AdminInputField(value = moderatorName, onValueChange = { moderatorName = it }, label = "Moderator Name")
                        AdminInputField(value = clubEmail, onValueChange = { clubEmail = it }, label = "Club Official Email")
                        AdminInputField(value = websiteUrl, onValueChange = { websiteUrl = it }, label = "Club Website URL")

                        OutlinedButton(
                            onClick = { launcher.launch("image/*") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(4.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite)
                        ) {
                            Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(if (imageUri != null) "Image Selected" else "Choose Club Image")
                        }

                        Box {
                            OutlinedButton(onClick = { deptExpanded = true }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp)) {
                                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                    Text(selectedDept, color = TextWhite)
                                    Icon(Icons.Default.ArrowDropDown, null, tint = TextWhite)
                                }
                            }
                            DropdownMenu(expanded = deptExpanded, onDismissRequest = { deptExpanded = false }) {
                                departments.forEach { dept ->
                                    DropdownMenuItem(text = { Text(dept) }, onClick = { selectedDept = dept; deptExpanded = false })
                                }
                            }
                        }

                        Box {
                            OutlinedButton(onClick = { typeExpanded = true }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp)) {
                                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                    Text(selectedType, color = TextWhite)
                                    Icon(Icons.Default.ArrowDropDown, null, tint = TextWhite)
                                }
                            }
                            DropdownMenu(expanded = typeExpanded, onDismissRequest = { typeExpanded = false }) {
                                types.forEach { type ->
                                    DropdownMenuItem(text = { Text(type) }, onClick = { selectedType = type; typeExpanded = false })
                                }
                            }
                        }

                        // --- ফিক্সড সেভ লজিক ---
                        Button(
                            onClick = {
                                if (clubName.isNotEmpty()) {
                                    isUploading = true
                                    val finalId = if (isEditMode) editingClubId else db.collection("clubs").document().id

                                    if (imageUri != null) {
                                        val fileName = "club_images/${UUID.randomUUID()}.jpg"
                                        val storageRef = storage.reference.child(fileName)

                                        // লজিক আপডেট: continueWithTask ব্যবহার করা হয়েছে
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
                                                    val clubData = Club(
                                                        id = finalId,
                                                        name = clubName,
                                                        moderator = moderatorName,
                                                        email = clubEmail,
                                                        website = websiteUrl,
                                                        department = selectedDept,
                                                        type = selectedType,
                                                        imageUrl = downloadUrl,
                                                        imageName = fileName,
                                                        description = description,
                                                        establishedDate = establishedDate
                                                    )

                                                    db.collection("clubs").document(finalId).set(clubData)
                                                        .addOnSuccessListener {
                                                            isUploading = false; isEditMode = false; imageUri = null
                                                            clubName = ""; description = ""; establishedDate = ""; moderatorName = ""; clubEmail = ""; websiteUrl = ""
                                                            Toast.makeText(context, "Saved Successfully!", Toast.LENGTH_SHORT).show()
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
                                        val clubData = Club(
                                            id = finalId, name = clubName, moderator = moderatorName, email = clubEmail,
                                            website = websiteUrl, department = selectedDept, type = selectedType,
                                            imageUrl = currentImageUrl, imageName = currentImageName,
                                            description = description, establishedDate = establishedDate
                                        )
                                        db.collection("clubs").document(finalId).set(clubData).addOnSuccessListener {
                                            isUploading = false; isEditMode = false
                                            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show()
                                        }.addOnFailureListener {
                                            isUploading = false
                                            Toast.makeText(context, "Update Failed!", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isUploading,
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                        ) {
                            if (isUploading) CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                            else Text(if (isEditMode) "Save Changes" else "Add Club")
                        }
                    }
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth().background(PrimaryBlue).padding(10.dp)) {
                    Text("Name", modifier = Modifier.weight(1.2f), color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("Moderator", modifier = Modifier.weight(1f), color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("Action", modifier = Modifier.weight(0.8f), color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }

            items(clubList) { club ->
                Row(
                    modifier = Modifier.fillMaxWidth().background(CardBackground).padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(club.name, modifier = Modifier.weight(1.2f), color = TextWhite, fontSize = 12.sp)
                    Text(club.moderator, modifier = Modifier.weight(1f), color = TextGray, fontSize = 12.sp)
                    Row(Modifier.weight(0.8f), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Edit", color = Color.Green, modifier = Modifier.clickable {
                            isEditMode = true
                            editingClubId = club.id
                            clubName = club.name
                            description = club.description
                            establishedDate = club.establishedDate
                            moderatorName = club.moderator
                            clubEmail = club.email
                            websiteUrl = club.website
                            selectedDept = club.department
                            selectedType = club.type
                            currentImageUrl = club.imageUrl
                            currentImageName = club.imageName
                        })
                        Text("Delete", color = Color.Red, modifier = Modifier.clickable {
                            db.collection("clubs").document(club.id).delete().addOnSuccessListener {
                                if (club.imageName.isNotEmpty()) {
                                    storage.reference.child(club.imageName).delete()
                                }
                                Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }
                HorizontalDivider(color = Color.DarkGray, thickness = 0.5.dp)
            }
        }
    }
}

@Composable
fun AdminInputField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange,
        placeholder = { Text(label, color = TextGray) },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = PrimaryBlue),
        shape = RoundedCornerShape(4.dp),
        singleLine = true
    )
}