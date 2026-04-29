package com.example.baustclubh.ui.screens.resources

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

// নেভিগেশন স্টেট ট্র্যাকিং
enum class LibraryState { CATEGORY, DEPARTMENT, LEVEL_TERM, EXAM_TYPE, PDF_LIST }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(navController: NavController) {
    var currentState by remember { mutableStateOf(LibraryState.CATEGORY) }
    var selectedCategory by remember { mutableStateOf("") }
    var selectedDept by remember { mutableStateOf("") }
    var selectedLevelTerm by remember { mutableStateOf("") }
    var selectedExamType by remember { mutableStateOf("") }
    var searchText by remember { mutableStateOf("") }

    // ডাটা সেট
    val allCategories = listOf("Questions", "Notes", "Books", "Slides", "Lectures", "Papers", "Videos", "Projects")
    val departments = listOf("CSE", "EEE", "ME", "IPE", "CE", "BBA", "English")
    val levelTerms = listOf("L1 T1", "L1 T2", "L2 T1", "L2 T2", "L3 T1", "L3 T2", "L4 T1", "L4 T2")
    val examTypes = listOf("Final Exam", "Mid Term", "Class Test (CT)")

    // ব্যাক বাটন লজিক
    val onBack: () -> Unit = {
        when (currentState) {
            LibraryState.CATEGORY -> navController.navigateUp()
            LibraryState.DEPARTMENT -> currentState = LibraryState.CATEGORY
            LibraryState.LEVEL_TERM -> currentState = LibraryState.DEPARTMENT
            LibraryState.EXAM_TYPE -> currentState = LibraryState.LEVEL_TERM
            LibraryState.PDF_LIST -> currentState = LibraryState.EXAM_TYPE
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (currentState) {
                            LibraryState.CATEGORY -> "Academic Library"
                            LibraryState.DEPARTMENT -> "Select Department"
                            LibraryState.LEVEL_TERM -> "$selectedDept - Level & Term"
                            LibraryState.EXAM_TYPE -> "Select Exam Type"
                            LibraryState.PDF_LIST -> "$selectedLevelTerm ($selectedExamType)"
                        },
                        color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextWhite)
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
            // Search Bar (শুধুমাত্র প্রথম ধাপে)
            if (currentState == LibraryState.CATEGORY) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Search resources...", color = TextGray) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = PrimaryBlue) },
                    modifier = Modifier.fillMaxWidth(),
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
            }

            // ডাইনামিক লিস্ট কন্টেন্ট
            val displayList = when (currentState) {
                LibraryState.CATEGORY -> allCategories.filter { it.contains(searchText, ignoreCase = true) }
                LibraryState.DEPARTMENT -> departments
                LibraryState.LEVEL_TERM -> levelTerms
                LibraryState.EXAM_TYPE -> examTypes
                LibraryState.PDF_LIST -> listOf("Batch 18.pdf", "Batch 19.pdf", "Batch 20.pdf")
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(displayList) { item ->
                    LibraryCategoryCard(
                        title = item,
                        icon = when (currentState) {
                            LibraryState.CATEGORY -> getCategoryIcon(item)
                            LibraryState.DEPARTMENT -> "🏛️"
                            LibraryState.LEVEL_TERM -> "📂"
                            LibraryState.EXAM_TYPE -> "📝"
                            LibraryState.PDF_LIST -> "📄"
                        },
                        onClick = {
                            when (currentState) {
                                LibraryState.CATEGORY -> {
                                    selectedCategory = item
                                    if (item == "Questions") {
                                        currentState = LibraryState.DEPARTMENT
                                    } else {
                                        // অন্য ক্যাটাগরির জন্য কন্টেন্ট লিস্টে যাওয়ার লজিক
                                        currentState = LibraryState.PDF_LIST
                                    }
                                }
                                LibraryState.DEPARTMENT -> {
                                    selectedDept = item
                                    currentState = LibraryState.LEVEL_TERM
                                }
                                LibraryState.LEVEL_TERM -> {
                                    selectedLevelTerm = item
                                    currentState = LibraryState.EXAM_TYPE
                                }
                                LibraryState.EXAM_TYPE -> {
                                    selectedExamType = item
                                    currentState = LibraryState.PDF_LIST
                                }
                                LibraryState.PDF_LIST -> { /* PDF Open Logic */ }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LibraryCategoryCard(title: String, icon: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .height(110.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = icon, fontSize = 32.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = TextWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

fun getCategoryIcon(title: String): String {
    return when (title.lowercase()) {
        "questions" -> "📝"
        "notes" -> "📓"
        "books" -> "📚"
        "slides" -> "📊"
        "lectures" -> "🎓"
        "papers" -> "📄"
        "videos" -> "🎥"
        "projects" -> "💻"
        else -> "📁"
    }
}