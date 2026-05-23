package com.example.baustclubh.ui.screens.clubs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
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
import com.example.baustclubh.data.model.Event
import com.example.baustclubh.ui.theme.*
import com.example.baustclubh.ui.components.BottomNavBar // নিশ্চিত করুন এই ইমপোর্টটি আছে
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    var allEvents by remember { mutableStateOf<List<Event>>(listOf()) }
    var isLoading by remember { mutableStateOf(true) }

    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Tech", "Culture", "Sport", "Workshop")

    var selectedType by remember { mutableStateOf("All Types") }
    var isTypeExpanded by remember { mutableStateOf(false) }
    val eventTypes = listOf("All Types", "Seminar", "Contest", "Fest", "Webinar")

    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        db.collection("events").addSnapshotListener { snapshot, _ ->
            isLoading = false
            if (snapshot != null) {
                val eventsList = snapshot.documents.mapNotNull { document ->
                    val event = document.toObject(Event::class.java)
                    event?.copy(id = document.id)
                }
                allEvents = eventsList
            }
        }
    }

    val filteredEvents = allEvents.filter { event ->
        val matchesCategory = selectedCategory == "All" || event.category.equals(selectedCategory, ignoreCase = true)
        val matchesType = selectedType == "All Types" || event.type.equals(selectedType, ignoreCase = true)
        val matchesSearch = searchQuery.isEmpty() || event.title.contains(searchQuery, ignoreCase = true) || event.clubName.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesType && matchesSearch
    }

    // Scaffold ব্যবহার করা হয়েছে যাতে BottomNavBar সব সময় নিচে থাকে
    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        containerColor = Color(0xFFF3F4F6)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { navController.popBackStack() },
                    tint = Color(0xFF111827)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Upcoming Events",
                    color = Color(0xFF111827),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = selectedCategory == category
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) PrimaryBlue else Color.White)
                            .clickable { selectedCategory = category }
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Text(text = category, color = if (isSelected) Color.White else Color(0xFF4B5563), fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                    }
                }
            }

            Box(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                ExposedDropdownMenuBox(
                    expanded = isTypeExpanded,
                    onExpandedChange = { isTypeExpanded = !isTypeExpanded }
                ) {
                    OutlinedTextField(
                        value = if (selectedType == "All Types") "Filter by Event Type" else selectedType,
                        onValueChange = {}, readOnly = true,
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, null, tint = PrimaryBlue) },
                        modifier = Modifier.fillMaxWidth().menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White, focusedBorderColor = Color.Transparent, unfocusedBorderColor = Color.Transparent)
                    )
                    ExposedDropdownMenu(expanded = isTypeExpanded, onDismissRequest = { isTypeExpanded = false }, modifier = Modifier.background(Color.White)) {
                        eventTypes.forEach { type ->
                            DropdownMenuItem(text = { Text(type) }, onClick = { selectedType = type; isTypeExpanded = false })
                        }
                    }
                }
            }

            OutlinedTextField(
                value = searchQuery, onValueChange = { searchQuery = it },
                placeholder = { Text("Search events by title or club...") },
                leadingIcon = { Icon(Icons.Default.Search, null, tint = PrimaryBlue) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White, focusedBorderColor = Color.Transparent, unfocusedBorderColor = Color.Transparent)
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = PrimaryBlue) }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 20.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredEvents) { event ->
                        EventItemCard(event = event, onClick = { navController.navigate("event_details/$it") })
                    }
                }
            }
        }
    }
}

@Composable
fun EventItemCard(event: Event, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick(event.id) },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            if (event.imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = event.imageUrl,
                    contentDescription = "Event Banner",
                    modifier = Modifier.fillMaxWidth().height(160.dp).clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = event.title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarMonth, null, tint = PrimaryBlue, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "${event.date} | ${event.time}", color = Color(0xFF374151), fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, null, tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = event.location, color = Color(0xFF374151), fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Organized by: ${event.clubName}", color = PrimaryBlue, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}