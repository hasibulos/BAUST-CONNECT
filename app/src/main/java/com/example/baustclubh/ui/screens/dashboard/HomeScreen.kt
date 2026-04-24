package com.example.baustclubh.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.baustclubh.ui.theme.*
import com.example.baustclubh.viewmodel.AuthViewModel
import com.example.baustclubh.ui.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    var showMenu by remember { mutableStateOf(false) }

    // Debug log
    LaunchedEffect(currentUser) {
        println("HomeScreen - Current User: ${currentUser?.name}")
        println("HomeScreen - Student ID: ${currentUser?.studentId}")
        println("HomeScreen - Department: ${currentUser?.department}")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Dashboard",
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
                actions = {
                    IconButton(onClick = { showMenu = true }) {
                        Text(text = "⋮", color = TextWhite, fontSize = 24.sp)
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        containerColor = CardBackground
                    ) {
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("👤", fontSize = 18.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("My Profile", color = TextWhite)
                                }
                            },
                            onClick = {
                                showMenu = false
                                navController.navigate("profile")
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("🚪", fontSize = 18.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Logout", color = Color(0xFFE74C3C))
                                }
                            },
                            onClick = {
                                showMenu = false
                                authViewModel.logout()
                                navController.navigate("login") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
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
                .padding(20.dp)
        ) {
            // Welcome Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(PrimaryBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currentUser?.name?.take(1)?.uppercase() ?: currentUser?.studentId?.take(1)?.uppercase() ?: "U",
                            color = TextWhite,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Hello, ${currentUser?.name ?: currentUser?.studentId ?: "User"}! 👋",
                            color = TextWhite,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "ID: ${currentUser?.studentId ?: "N/A"}",
                            color = TextGray,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "${currentUser?.department ?: "Department"} | Batch: ${currentUser?.batch ?: "N/A"}",
                            color = PrimaryBlue,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "📧 ${currentUser?.email ?: "Email not set"}",
                            color = TextGray,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                    count = currentUser?.registeredClubs?.size?.toString() ?: "0",
                    title = "Clubs Joined",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    count = "12",
                    title = "Events Attended",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "📅 Upcoming Events",
                color = TextWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            EventItem(
                title = "Programming Contest",
                date = "Mar 22, 10:00 AM",
                venue = "Computer Lab"
            )
            EventItem(
                title = "Robotics Workshop",
                date = "Mar 25, 2:00 PM",
                venue = "Lab 502"
            )
            EventItem(
                title = "Cultural Night",
                date = "Mar 28, 6:00 PM",
                venue = "Auditorium"
            )
        }
    }
}

@Composable
fun StatCard(count: String, title: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count,
                color = PrimaryBlue,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = title,
                color = TextGray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun EventItem(title: String, date: String, venue: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    color = TextWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = date,
                    color = TextGray,
                    fontSize = 12.sp
                )
                Text(
                    text = venue,
                    color = PrimaryBlue,
                    fontSize = 11.sp
                )
            }
            Text(
                text = "→",
                color = PrimaryBlue,
                fontSize = 20.sp
            )
        }
    }
}