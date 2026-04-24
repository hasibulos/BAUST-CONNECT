package com.example.baustclubh.ui.screens.clubs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baustclubh.ui.theme.*
import com.example.baustclubh.ui.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubListScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }

    // All clubs data
    val allClubs = listOf(
        ClubData("CSE Programming Club", "156 Members", "Technical", "Spring 2026"),
        ClubData("BAUST Robotics Club", "84 Members", "Technical", "Active"),
        ClubData("Debate Club", "42 Members", "Cultural", "Recruiting"),
        ClubData("Cultural Club", "65 Members", "Cultural", "Active"),
        ClubData("Business Club", "38 Members", "Business", "Active"),
        ClubData("Sports Club", "92 Members", "Sports", "Spring 2026"),
        ClubData("Photography Club", "45 Members", "Creative", "Recruiting"),
        ClubData("Music Club", "52 Members", "Cultural", "Active")
    )

    // Filter clubs based on search
    val filteredClubs = if (searchText.isEmpty()) {
        allClubs
    } else {
        allClubs.filter {
            it.name.contains(searchText, ignoreCase = true) ||
                    it.category.contains(searchText, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "All Clubs",
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Text(text = "←", color = TextWhite, fontSize = 24.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundDark)
            )
        },
        bottomBar = { BottomNavBar(navController) },
        containerColor = BackgroundDark
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search clubs by name or category...", color = TextGray) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = PrimaryBlue)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = CardBackground,
                    focusedBorderColor = PrimaryBlue,
                    focusedLabelColor = PrimaryBlue,
                    cursorColor = PrimaryBlue
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Search Result Info
            if (searchText.isNotEmpty()) {
                Text(
                    text = "Found ${filteredClubs.size} clubs for \"$searchText\"",
                    color = TextGray,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Clubs List
            if (filteredClubs.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "🔍", fontSize = 48.sp)
                        Text(text = "No clubs found", color = TextGray, fontSize = 16.sp)
                        Text(text = "Try searching with different keywords", color = TextGray, fontSize = 12.sp)
                    }
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(filteredClubs) { club ->
                        ClubCard(
                            name = club.name,
                            members = club.members,
                            status = club.status,
                            category = club.category,
                            onClick = {
                                navController.navigate("club_detail/${club.name}")
                            }
                        )
                    }
                }
            }
        }
    }
}

data class ClubData(
    val name: String,
    val members: String,
    val category: String,
    val status: String
)

@Composable
fun ClubCard(
    name: String,
    members: String,
    status: String,
    category: String,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    color = TextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$members • $category",
                    color = TextGray,
                    fontSize = 12.sp
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = status,
                    color = when {
                        status.contains("Active") -> Color(0xFF4CAF50)
                        status.contains("Recruiting") -> PrimaryBlue
                        else -> Color(0xFFFF9800)
                    },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "View →",
                    color = PrimaryBlue,
                    fontSize = 11.sp
                )
            }
        }
    }
}