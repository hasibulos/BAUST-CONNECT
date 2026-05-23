package com.example.baustclubh.ui.screens.alumni

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search // 👈 সার্চ আইকনের জন্য ইম্পোর্ট
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
import coil.compose.AsyncImage
import com.example.baustclubh.ui.theme.*
import com.example.baustclubh.ui.components.BottomNavBar
import com.example.baustclubh.viewmodel.AuthViewModel
import com.google.firebase.firestore.FirebaseFirestore
import java.net.URLEncoder

data class Alumni(
    val id: String = "",
    val name: String = "",
    val batch: String = "",
    val position: String = "",
    val icon: String = "",
    val email: String = "",
    val linkedin: String = "",
    val facebook: String = "",
    val bio: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val context = LocalContext.current

    val sharedPreferences = remember { context.getSharedPreferences("baust_club_prefs", Context.MODE_PRIVATE) }
    val savedRole = sharedPreferences.getString("user_role", null)
    val role = savedRole ?: currentUser?.role ?: "student"

    val db = FirebaseFirestore.getInstance()
    var alumniList by remember { mutableStateOf<List<Alumni>>(listOf()) }
    var isLoading by remember { mutableStateOf(true) }

    // 🔍 ফিল্টারিং এবং সার্চের জন্য স্টেট ডিক্লেয়ারেশন
    var searchQuery by remember { mutableStateOf("") } // 👈 নতুন সার্চ কুয়েরি স্টেট
    var selectedDept by remember { mutableStateOf("All Dept") }
    var selectedBatch by remember { mutableStateOf("All Batch") }

    var isDeptMenuExpanded by remember { mutableStateOf(false) }
    var isBatchMenuExpanded by remember { mutableStateOf(false) }

    val departments = listOf("All Dept", "CSE", "EEE", "ME", "IPE", "CE", "BBA", "English")

    LaunchedEffect(Unit) {
        db.collection("alumni").addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                alumniList = snapshot.toObjects(Alumni::class.java)
            }
            isLoading = false
        }
    }

    val batchList = remember(alumniList) {
        val uniqueBatches = alumniList.map { it.batch.split(" ").lastOrNull() ?: "" }
            .filter { it.isNotBlank() }
            .distinct()
            .sorted()
        listOf("All Batch") + uniqueBatches
    }

    // ⚡ ফিল্টার এবং সার্চ লজিক একসাথে মার্জ করা হয়েছে (যেকোনো কিছু সার্চ করলেই আসবে)
    val filteredAlumniList = alumniList.filter { alumni ->
        val matchesDept = selectedDept == "All Dept" || alumni.batch.contains(selectedDept, ignoreCase = true)
        val matchesBatch = selectedBatch == "All Batch" || alumni.batch.contains(selectedBatch, ignoreCase = true)

        // সার্চ কোয়েরি ফাঁকা থাকলে সব দেখাবে, আর কিছু লিখলে নাম, ব্যাচ, ডিপার্টমেন্ট বা পজিশন ম্যাচ করবে
        val matchesSearch = searchQuery.isBlank() ||
                alumni.name.contains(searchQuery, ignoreCase = true) ||
                alumni.batch.contains(searchQuery, ignoreCase = true) ||
                alumni.position.contains(searchQuery, ignoreCase = true)

        matchesDept && matchesBatch && matchesSearch
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Alumni Network",
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Text(
                            text = "←",
                            color = TextWhite,
                            fontSize = 24.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundDark
                )
            )
        },
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = {
            if (role == "super_admin" || role == "dept_admin") {
                FloatingActionButton(
                    onClick = { navController.navigate("manage_alumni") },
                    containerColor = PrimaryBlue,
                    contentColor = TextWhite,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Alumni")
                }
            }
        },
        containerColor = BackgroundDark
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "🎓", fontSize = 48.sp)
                    Text(
                        text = "BAUST Alumni Association",
                        color = TextWhite,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Connect with graduates from BAUST",
                        color = TextGray,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ================= 🎛️ FILTERS SECTION =================
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // ১. Department Filter Dropdown
                ExposedDropdownMenuBox(
                    expanded = isDeptMenuExpanded,
                    onExpandedChange = { isDeptMenuExpanded = !isDeptMenuExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedButton(
                        onClick = { isDeptMenuExpanded = true },
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = selectedDept, fontSize = 13.sp, maxLines = 1)
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = PrimaryBlue)
                        }
                    }
                    ExposedDropdownMenu(
                        expanded = isDeptMenuExpanded,
                        onDismissRequest = { isDeptMenuExpanded = false },
                        modifier = Modifier.background(CardBackground)
                    ) {
                        departments.forEach { dept ->
                            DropdownMenuItem(
                                text = { Text(dept, color = TextWhite) },
                                onClick = {
                                    selectedDept = dept
                                    isDeptMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                // ২. Batch Filter Dropdown
                ExposedDropdownMenuBox(
                    expanded = isBatchMenuExpanded,
                    onExpandedChange = { isBatchMenuExpanded = !isBatchMenuExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedButton(
                        onClick = { isBatchMenuExpanded = true },
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = selectedBatch, fontSize = 13.sp, maxLines = 1)
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = PrimaryBlue)
                        }
                    }
                    ExposedDropdownMenu(
                        expanded = isBatchMenuExpanded,
                        onDismissRequest = { isBatchMenuExpanded = false },
                        modifier = Modifier.background(CardBackground)
                    ) {
                        batchList.forEach { batch ->
                            DropdownMenuItem(
                                text = { Text(batch, color = TextWhite) },
                                onClick = {
                                    selectedBatch = batch
                                    isBatchMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ================= 🔍 NEW SEARCH BAR SECTION =================
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Search by name, batch, company...", color = TextGray) },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon", tint = PrimaryBlue) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedContainerColor = CardBackground,
                    unfocusedContainerColor = CardBackground,
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = CardBackground.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Alumni List Title
            Text(
                text = "Notable Alumni",
                color = TextWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Alumni List Rendering
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PrimaryBlue)
                }
            } else if (filteredAlumniList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No alumni found for this criteria", color = TextGray, fontSize = 14.sp)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredAlumniList) { alumni ->
                        AlumniCard(
                            alumni = alumni,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AlumniCard(alumni: Alumni, navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                if (alumni.icon.startsWith("http")) {
                    AsyncImage(
                        model = alumni.icon,
                        contentDescription = "Alumni Profile Pic",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .background(PrimaryBlue, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = alumni.name.take(1).uppercase(),
                            fontSize = 18.sp,
                            color = TextWhite,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = alumni.name,
                    color = TextWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = alumni.batch,
                    color = PrimaryBlue,
                    fontSize = 12.sp
                )
                Text(
                    text = alumni.position,
                    color = TextGray,
                    fontSize = 12.sp
                )
            }

            TextButton(
                onClick = {
                    val encodedIcon = if (alumni.icon.isNotBlank()) URLEncoder.encode(alumni.icon, "UTF-8") else "null"
                    val encodedBio = if (alumni.bio.isNotBlank()) URLEncoder.encode(alumni.bio, "UTF-8") else "null"
                    val encodedPosition = if (alumni.position.isNotBlank()) URLEncoder.encode(alumni.position, "UTF-8") else "null"

                    navController.navigate(
                        "alumni_profile/${alumni.name}/${alumni.batch}/$encodedPosition/$encodedIcon/${alumni.email.ifBlank { "null" }}/${alumni.linkedin.ifBlank { "null" }}/${alumni.facebook.ifBlank { "null" }}/$encodedBio"
                    )
                }
            ) {
                Text(
                    text = "View Profile",
                    color = PrimaryBlue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}