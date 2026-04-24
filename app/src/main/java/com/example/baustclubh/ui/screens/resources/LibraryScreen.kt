package com.example.baustclubh.ui.screens.resources

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baustclubh.ui.theme.*
import com.example.baustclubh.ui.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }

    // Resource categories
    val allCategories = listOf(
        "Questions", "Notes", "Books", "Slides",
        "Lectures", "Papers", "Videos", "Projects"
    )

    // Filter categories based on search
    val filteredCategories = if (searchText.isEmpty()) {
        allCategories
    } else {
        allCategories.filter { it.contains(searchText, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Academic Library",
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
                placeholder = { Text("Search resources...", color = TextGray) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        tint = PrimaryBlue
                    )
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
                    text = "Showing results for: \"$searchText\"",
                    color = TextGray,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Categories Grid
            if (filteredCategories.isEmpty()) {
                // No results found
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "🔍",
                            fontSize = 48.sp
                        )
                        Text(
                            text = "No results found",
                            color = TextGray,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Try searching with different keywords",
                            color = TextGray,
                            fontSize = 12.sp
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredCategories) { category ->
                        LibraryCategoryCard(
                            title = category,
                            onClick = {
                                // Handle category click
                                searchText = category
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LibraryCategoryCard(title: String, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon based on category
            Text(
                text = when (title.lowercase()) {
                    "questions" -> "📝"
                    "notes" -> "📓"
                    "books" -> "📚"
                    "slides" -> "📊"
                    "lectures" -> "🎓"
                    "papers" -> "📄"
                    "videos" -> "🎥"
                    "projects" -> "💻"
                    else -> "📁"
                },
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = TextWhite,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}