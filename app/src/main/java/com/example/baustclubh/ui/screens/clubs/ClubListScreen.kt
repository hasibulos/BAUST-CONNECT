package com.example.baustclubh.ui.screens.clubs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.baustclubh.ui.theme.*
import com.example.baustclubh.viewmodel.ClubViewModel
import com.example.baustclubh.ui.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubListScreen(
    navController: NavController,
    clubViewModel: ClubViewModel
) {
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Tech", "Culture", "Sport", "Business")

    var selectedDept by remember { mutableStateOf("All Dept") }
    var isDeptMenuExpanded by remember { mutableStateOf(false) }
    val departments = listOf("All Dept", "CSE", "EEE", "ME", "IPE", "CE", "BBA", "English")

    val clubList by clubViewModel.clubs.collectAsState()

    // ⚡ এরর ফিক্সড ফিল্টার লজিক: club.category বাদ দিয়ে শুধু name এবং department চেক করা হচ্ছে
    val filteredClubs = clubList.filter { club ->
        val matchesCategory = selectedCategory == "All" ||
                club.name.contains(selectedCategory, ignoreCase = true) ||
                club.department.contains(selectedCategory, ignoreCase = true)

        val matchesDept = selectedDept == "All Dept" || club.department.contains(selectedDept, ignoreCase = true)
        val matchesSearch = club.name.isNotEmpty() && club.name.contains(searchText, ignoreCase = true)

        matchesCategory && matchesDept && matchesSearch
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Available Clubs", color = TextWhite, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextWhite)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = BackgroundDark)
            )
        },
        bottomBar = { BottomNavBar(navController) },
        containerColor = BackgroundDark
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // 🏷️ CATEGORY CHIPS ROW
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = CardBackground,
                            selectedContainerColor = PrimaryBlue,
                            labelColor = TextGray,
                            selectedLabelColor = TextWhite
                        ),
                        border = null,
                        shape = CircleShape
                    )
                }
            }

            // 🔽 DEPARTMENT DROPDOWN LAYER
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = isDeptMenuExpanded,
                    onExpandedChange = { isDeptMenuExpanded = !isDeptMenuExpanded }
                ) {
                    OutlinedButton(
                        onClick = { isDeptMenuExpanded = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = CardBackground,
                            contentColor = TextWhite
                        ),
                        border = null
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (selectedDept == "All Dept") "Filter by Department" else "Dept: $selectedDept",
                                fontSize = 14.sp,
                                color = if (selectedDept == "All Dept") TextGray else TextWhite
                            )
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
            }

            Spacer(modifier = Modifier.height(4.dp))

            // 🔍 SEARCH BAR SECTION
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search clubs by name...", color = TextGray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = PrimaryBlue) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedContainerColor = CardBackground,
                    unfocusedContainerColor = CardBackground,
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = PrimaryBlue
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 📜 CLUBS LIST VIEW
            if (filteredClubs.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No clubs found for this criteria!", color = TextGray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(filteredClubs) { club ->
                        ClubCardItem(
                            name = club.name,
                            dept = club.department,
                            imageUrl = club.imageUrl
                        ) {
                            navController.navigate("club_details/${club.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ClubCardItem(name: String, dept: String, imageUrl: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(PrimaryBlue.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                if (imageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Club Logo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        tint = PrimaryBlue,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(name, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(dept, color = TextGray, fontSize = 12.sp)
            }

            Text("Details →", color = PrimaryBlue, fontSize = 12.sp)
        }
    }
}