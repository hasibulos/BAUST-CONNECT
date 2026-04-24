package com.example.baustclubh.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baustclubh.ui.theme.*

@Composable
fun AdminDashboard(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(16.dp)
    ) {
        Text(
            text = "Admin Dashboard",
            color = TextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Welcome back, Admin",
            color = TextGray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                AdminCard(
                    title = "Manage Clubs",
                    description = "Add/Edit/Delete Clubs",
                    icon = "👥",
                    navController = navController,
                    route = "admin_clubs"
                )
            }
            item {
                AdminCard(
                    title = "Pending Applications",
                    description = "Approve/Reject Requests",
                    icon = "📝",
                    navController = navController,
                    route = "admin_applications"
                )
            }
            item {
                AdminCard(
                    title = "Manage Members",
                    description = "View All Members",
                    icon = "👤",
                    navController = navController,
                    route = "admin_members"
                )
            }
            item {
                AdminCard(
                    title = "Add Events",
                    description = "Create New Events",
                    icon = "📅",
                    navController = navController,
                    route = "admin_events"
                )
            }
            item {
                AdminCard(
                    title = "QR Attendance",
                    description = "Manage Attendance",
                    icon = "📷",
                    navController = navController,
                    route = "qr_scan"
                )
            }
        }
    }
}

@Composable
fun AdminCard(
    title: String,
    description: String,
    icon: String,
    navController: NavController,
    route: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp),
        onClick = { navController.navigate(route) }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = icon,
                fontSize = 32.sp
            )
            Column {
                Text(
                    text = title,
                    color = TextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    color = TextGray,
                    fontSize = 12.sp
                )
            }
        }
    }
}