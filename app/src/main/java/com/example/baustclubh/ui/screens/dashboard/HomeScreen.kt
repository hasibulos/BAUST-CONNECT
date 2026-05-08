//package com.example.baustclubh.ui.screens.dashboard
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AdminPanelSettings
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.baustclubh.ui.theme.*
//import com.example.baustclubh.viewmodel.AuthViewModel
//import com.example.baustclubh.ui.components.BottomNavBar
//import java.util.Calendar
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeScreen(
//    navController: NavController,
//    authViewModel: AuthViewModel
//) {
//    val currentUser by authViewModel.currentUser.collectAsState()
//    val role = currentUser?.role ?: "student" // রোল চেক করার জন্য
//    var searchQuery by remember { mutableStateOf("") }
//    var showMenu by remember { mutableStateOf(false) }
//
//    val greeting = remember {
//        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
//        when (hour) {
//            in 5..11 -> "Good morning"
//            in 12..16 -> "Good afternoon"
//            in 17..20 -> "Good evening"
//            else -> "Good night"
//        }
//    }
//
//    val allEvents = listOf(
//        Triple("Programming Contest", "Mar 22, 10:00 AM", "💻"),
//        Triple("Robotics Workshop", "Mar 25, 2:00 PM", "🤖"),
//        Triple("Cultural Night", "Mar 28, 6:00 PM", "🎭")
//    )
//
//    val filteredEvents = remember(searchQuery) {
//        if (searchQuery.isEmpty()) allEvents
//        else allEvents.filter { it.first.contains(searchQuery, ignoreCase = true) }
//    }
//
//    Scaffold(
//        bottomBar = { BottomNavBar(navController) },
//        // --- নতুন অ্যাডমিন বাটন এখানে যোগ করা হয়েছে ---
//        floatingActionButton = {
//            if (role == "super_admin" || role == "dept_admin") {
//                ExtendedFloatingActionButton(
//                    onClick = { navController.navigate("admin_dashboard") },
//                    containerColor = PrimaryBlue,
//                    contentColor = TextWhite,
//                    icon = { Icon(Icons.Default.AdminPanelSettings, contentDescription = null) },
//                    text = { Text("Back to Admin") }
//                )
//            }
//        },
//        containerColor = BackgroundDark
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp)
//        ) {
//            // Header Section
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column {
//                    Text("$greeting,", color = TextGray, fontSize = 14.sp)
//                    Text(
//                        text = "${currentUser?.name ?: "User"} 👋",
//                        color = TextWhite,
//                        fontSize = 22.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//
//                Box(
//                    modifier = Modifier
//                        .size(45.dp)
//                        .clip(CircleShape)
//                        .background(PrimaryBlue)
//                        .clickable { showMenu = true },
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = currentUser?.name?.take(1)?.uppercase() ?: "H",
//                        color = TextWhite,
//                        fontWeight = FontWeight.Bold
//                    )
//
//                    DropdownMenu(
//                        expanded = showMenu,
//                        onDismissRequest = { showMenu = false },
//                        containerColor = CardBackground
//                    ) {
//                        DropdownMenuItem(
//                            text = { Text("👤 My Profile", color = TextWhite) },
//                            onClick = {
//                                showMenu = false
//                                navController.navigate("profile")
//                            }
//                        )
//                        DropdownMenuItem(
//                            text = { Text("🚪 Logout", color = Color(0xFFE74C3C)) },
//                            onClick = {
//                                showMenu = false
//                                authViewModel.logout()
//                                navController.navigate("login") {
//                                    popUpTo("home") { inclusive = true }
//                                }
//                            }
//                        )
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            OutlinedTextField(
//                value = searchQuery,
//                onValueChange = { searchQuery = it },
//                placeholder = { Text("Search clubs, events, resources...", color = TextGray, fontSize = 14.sp) },
//                leadingIcon = { Text("🔍", modifier = Modifier.padding(start = 8.dp)) },
//                trailingIcon = {
//                    if (searchQuery.isNotEmpty()) {
//                        IconButton(onClick = { searchQuery = "" }) {
//                            Text("✕", color = TextWhite)
//                        }
//                    }
//                },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true,
//                shape = RoundedCornerShape(12.dp),
//                colors = OutlinedTextFieldDefaults.colors(
//                    focusedContainerColor = CardBackground,
//                    unfocusedContainerColor = CardBackground,
//                    focusedBorderColor = PrimaryBlue,
//                    unfocusedBorderColor = Color.Transparent,
//                    cursorColor = PrimaryBlue,
//                    focusedTextColor = TextWhite,
//                    unfocusedTextColor = TextWhite
//                )
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Stats Grid
//            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
//                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
//                    StatCard(
//                        count = currentUser?.registeredClubs?.size?.toString() ?: "0",
//                        title = "Clubs Joined",
//                        modifier = Modifier.weight(1f)
//                    )
//                    StatCard(
//                        count = "12",
//                        title = "Events Attended",
//                        modifier = Modifier.weight(1f)
//                    )
//                }
//                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
//                    StatCard(count = "3", title = "Certificates", modifier = Modifier.weight(1f))
//                    StatCard(count = "87%", title = "Attendance Rate", modifier = Modifier.weight(1f))
//                }
//            }
//
//            Spacer(modifier = Modifier.height(28.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = if (searchQuery.isEmpty()) "Upcoming Events" else "Search Results",
//                    color = TextWhite,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Text("See all", color = PrimaryBlue, fontSize = 14.sp, modifier = Modifier.clickable { })
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            if (filteredEvents.isEmpty()) {
//                Text(
//                    "No results found for \"$searchQuery\"",
//                    color = TextGray,
//                    modifier = Modifier.padding(top = 20.dp).align(Alignment.CenterHorizontally)
//                )
//            } else {
//                filteredEvents.forEach { event ->
//                    EventItem(title = event.first, date = event.second, icon = event.third)
//                    Spacer(modifier = Modifier.height(10.dp))
//                }
//            }
//        }
//    }
//}
//
//// StatCard এবং EventItem একই থাকবে
//@Composable
//fun StatCard(count: String, title: String, modifier: Modifier = Modifier) {
//    Card(
//        modifier = modifier,
//        colors = CardDefaults.cardColors(containerColor = CardBackground),
//        shape = RoundedCornerShape(16.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp),
//            horizontalAlignment = Alignment.Start
//        ) {
//            Text(text = count, color = PrimaryBlue, fontSize = 24.sp, fontWeight = FontWeight.Bold)
//            Text(text = title, color = TextGray, fontSize = 12.sp)
//        }
//    }
//}
//
//@Composable
//fun EventItem(title: String, date: String, icon: String) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(containerColor = CardBackground),
//        shape = RoundedCornerShape(16.dp)
//    ) {
//        Row(
//            modifier = Modifier.padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Box(
//                modifier = Modifier.size(40.dp).background(PrimaryBlue.copy(0.1f), CircleShape),
//                contentAlignment = Alignment.Center
//            ) { Text(icon, fontSize = 20.sp) }
//            Spacer(modifier = Modifier.width(16.dp))
//            Column(modifier = Modifier.weight(1f)) {
//                Text(title, color = TextWhite, fontSize = 15.sp, fontWeight = FontWeight.Bold)
//                Text(date, color = TextGray, fontSize = 12.sp)
//            }
//            Text("→", color = PrimaryBlue, fontSize = 18.sp)
//        }
//    }
//}

package com.example.baustclubh.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baustclubh.ui.theme.*
import com.example.baustclubh.viewmodel.AuthViewModel
import com.example.baustclubh.ui.components.BottomNavBar
import com.example.baustclubh.data.model.Event
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val role = currentUser?.role ?: "student"
    var searchQuery by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }

    // ১. রিয়েল ডাটা লোড করার জন্য স্টেট
    val db = FirebaseFirestore.getInstance()
    var eventList by remember { mutableStateOf<List<Event>>(listOf()) }

    LaunchedEffect(Unit) {
        // ফায়ারবেস থেকে লেটেস্ট ৩টি ইভেন্ট হোম স্ক্রিনের জন্য নিয়ে আসা
        db.collection("events").limit(3).addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                eventList = snapshot.toObjects(Event::class.java)
            }
        }
    }

    val greeting = remember {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        when (hour) {
            in 5..11 -> "Good morning"
            in 12..16 -> "Good afternoon"
            in 17..20 -> "Good evening"
            else -> "Good night"
        }
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = {
            if (role == "super_admin" || role == "dept_admin") {
                ExtendedFloatingActionButton(
                    onClick = { navController.navigate("admin_dashboard") },
                    containerColor = PrimaryBlue,
                    contentColor = TextWhite,
                    icon = { Icon(Icons.Default.AdminPanelSettings, contentDescription = null) },
                    text = { Text("Back to Admin") }
                )
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
            // Header Section (Same as before)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("$greeting,", color = TextGray, fontSize = 14.sp)
                    Text(
                        text = "${currentUser?.name ?: "User"} 👋",
                        color = TextWhite,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlue)
                        .clickable { showMenu = true },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentUser?.name?.take(1)?.uppercase() ?: "H",
                        color = TextWhite,
                        fontWeight = FontWeight.Bold
                    )

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        containerColor = CardBackground
                    ) {
                        DropdownMenuItem(
                            text = { Text("👤 My Profile", color = TextWhite) },
                            onClick = {
                                showMenu = false
                                navController.navigate("profile")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("🚪 Logout", color = Color(0xFFE74C3C)) },
                            onClick = {
                                showMenu = false
                                authViewModel.logout()
                                navController.navigate("login") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Search Bar (Same as before)
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search clubs, events...", color = TextGray, fontSize = 14.sp) },
                leadingIcon = { Text("🔍", modifier = Modifier.padding(start = 8.dp)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardBackground,
                    unfocusedContainerColor = CardBackground,
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Stats Grid (Same as before)
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatCard(count = "0", title = "Clubs Joined", modifier = Modifier.weight(1f))
                    StatCard(count = "12", title = "Events Attended", modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ২. রিয়েল ইভেন্ট সেকশন এবং নেভিগেশন ফিক্স
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Upcoming Events",
                    color = TextWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                // "See all" এ ক্লিক করলে ইভেন্ট লিস্টে যাবে
                Text(
                    text = "See all",
                    color = PrimaryBlue,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { navController.navigate("event_list") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // রিয়েল ইভেন্ট লিস্ট রেন্ডারিং
            if (eventList.isEmpty()) {
                Text("No upcoming events", color = TextGray, modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                eventList.forEach { event ->
                    EventItem(
                        title = event.title,
                        date = "${event.date} | ${event.time}",
                        onClick = {
                            // ইভেন্টে ক্লিক করলে ডিটেইলসে যাবে
                            navController.navigate("event_details/${event.id}")
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
fun StatCard(count: String, title: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = count, color = PrimaryBlue, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text = title, color = TextGray, fontSize = 12.sp)
        }
    }
}

@Composable
fun EventItem(title: String, date: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(40.dp).background(PrimaryBlue.copy(0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) { Text("📅", fontSize = 20.sp) }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = TextWhite, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(date, color = TextGray, fontSize = 12.sp)
            }
            Text("→", color = PrimaryBlue, fontSize = 18.sp)
        }
    }
}























/// without dropdown code

//package com.example.baustclubh.ui.screens.dashboard
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.baustclubh.ui.theme.*
//import com.example.baustclubh.viewmodel.AuthViewModel
//import com.example.baustclubh.ui.components.BottomNavBar
//import java.util.Calendar
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeScreen(
//    navController: NavController,
//    authViewModel: AuthViewModel
//) {
//    val currentUser by authViewModel.currentUser.collectAsState()
//    var searchQuery by remember { mutableStateOf("") }
//
//    // --- ১. ডাইনামিক টাইম উইশ লজিক ---
//    val greeting = remember {
//        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
//        when (hour) {
//            in 5..11 -> "Good morning"
//            in 12..16 -> "Good afternoon"
//            in 17..20 -> "Good evening"
//            else -> "Good night"
//        }
//    }
//
//    // --- ২. স্যাম্পল ডাটা লিস্ট (সার্চ করার জন্য) ---
//    val allEvents = listOf(
//        Triple("Programming Contest", "Mar 22, 10:00 AM", "💻"),
//        Triple("Robotics Workshop", "Mar 25, 2:00 PM", "🤖"),
//        Triple("Cultural Night", "Mar 28, 6:00 PM", "🎭")
//    )
//
//    // --- ৩. ফিল্টারিং লজিক ---
//    val filteredEvents = remember(searchQuery) {
//        if (searchQuery.isEmpty()) allEvents
//        else allEvents.filter { it.first.contains(searchQuery, ignoreCase = true) }
//    }
//
//    Scaffold(
//        bottomBar = { BottomNavBar(navController) },
//        containerColor = BackgroundDark
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp)
//        ) {
//            // Header Section
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column {
//                    Text("$greeting,", color = TextGray, fontSize = 14.sp)
//                    Text(
//                        text = "${currentUser?.name ?: "User"} 👋",
//                        color = TextWhite,
//                        fontSize = 22.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//
//                // এখানে ড্রপডাউন মেনু সরিয়ে সরাসরি নেভিগেশন দেওয়া হয়েছে
//                Box(
//                    modifier = Modifier
//                        .size(45.dp)
//                        .clip(CircleShape)
//                        .background(PrimaryBlue)
//                        .clickable {
//                            navController.navigate("profile") // সরাসরি প্রোফাইলে নিয়ে যাবে
//                        },
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = currentUser?.name?.take(1)?.uppercase() ?: "H",
//                        color = TextWhite,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            // Search Bar
//            OutlinedTextField(
//                value = searchQuery,
//                onValueChange = { searchQuery = it },
//                placeholder = { Text("Search clubs, events, resources...", color = TextGray, fontSize = 14.sp) },
//                leadingIcon = { Text("🔍", modifier = Modifier.padding(start = 8.dp)) },
//                trailingIcon = {
//                    if (searchQuery.isNotEmpty()) {
//                        IconButton(onClick = { searchQuery = "" }) {
//                            Text("✕", color = TextWhite)
//                        }
//                    }
//                },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true,
//                shape = RoundedCornerShape(12.dp),
//                colors = OutlinedTextFieldDefaults.colors(
//                    focusedContainerColor = CardBackground,
//                    unfocusedContainerColor = CardBackground,
//                    focusedBorderColor = PrimaryBlue,
//                    unfocusedBorderColor = Color.Transparent,
//                    cursorColor = PrimaryBlue,
//                    focusedTextColor = TextWhite,
//                    unfocusedTextColor = TextWhite
//                )
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Stats Grid
//            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
//                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
//                    StatCard(
//                        count = currentUser?.registeredClubs?.size?.toString() ?: "0",
//                        title = "Clubs Joined",
//                        modifier = Modifier.weight(1f)
//                    )
//                    StatCard(
//                        count = "12",
//                        title = "Events Attended",
//                        modifier = Modifier.weight(1f)
//                    )
//                }
//                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
//                    StatCard(count = "3", title = "Certificates", modifier = Modifier.weight(1f))
//                    StatCard(count = "87%", title = "Attendance Rate", modifier = Modifier.weight(1f))
//                }
//            }
//
//            Spacer(modifier = Modifier.height(28.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = if (searchQuery.isEmpty()) "Upcoming Events" else "Search Results",
//                    color = TextWhite,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Text("See all", color = PrimaryBlue, fontSize = 14.sp, modifier = Modifier.clickable { })
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // ইভেন্ট লিস্ট রেন্ডার করা
//            if (filteredEvents.isEmpty()) {
//                Text(
//                    "No results found for \"$searchQuery\"",
//                    color = TextGray,
//                    modifier = Modifier.padding(top = 20.dp).align(Alignment.CenterHorizontally)
//                )
//            } else {
//                filteredEvents.forEach { event ->
//                    EventItem(title = event.first, date = event.second, icon = event.third)
//                    Spacer(modifier = Modifier.height(10.dp))
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun StatCard(count: String, title: String, modifier: Modifier = Modifier) {
//    Card(
//        modifier = modifier,
//        colors = CardDefaults.cardColors(containerColor = CardBackground),
//        shape = RoundedCornerShape(16.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp),
//            horizontalAlignment = Alignment.Start
//        ) {
//            Text(text = count, color = PrimaryBlue, fontSize = 24.sp, fontWeight = FontWeight.Bold)
//            Text(text = title, color = TextGray, fontSize = 12.sp)
//        }
//    }
//}
//
//@Composable
//fun EventItem(title: String, date: String, icon: String) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(containerColor = CardBackground),
//        shape = RoundedCornerShape(16.dp)
//    ) {
//        Row(
//            modifier = Modifier.padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Box(
//                modifier = Modifier.size(40.dp).background(PrimaryBlue.copy(0.1f), CircleShape),
//                contentAlignment = Alignment.Center
//            ) { Text(icon, fontSize = 20.sp) }
//            Spacer(modifier = Modifier.width(16.dp))
//            Column(modifier = Modifier.weight(1f)) {
//                Text(title, color = TextWhite, fontSize = 15.sp, fontWeight = FontWeight.Bold)
//                Text(date, color = TextGray, fontSize = 12.sp)
//            }
//            Text("→", color = PrimaryBlue, fontSize = 18.sp)
//        }
//    }
//}