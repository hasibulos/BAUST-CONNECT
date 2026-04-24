package com.example.baustclubh.ui.screens.clubs

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
fun MyApplicationsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(16.dp)
    ) {
        Text(
            text = "My Applications",
            color = TextWhite,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                ApplicationStatusCard(
                    clubName = "CSE Programming Club",
                    status = "Pending",
                    date = "Applied on Mar 15, 2026"
                )
            }
            item {
                ApplicationStatusCard(
                    clubName = "BAUST Robotics Club",
                    status = "Approved",
                    date = "Applied on Mar 10, 2026"
                )
            }
            item {
                ApplicationStatusCard(
                    clubName = "Debate Club",
                    status = "Rejected",
                    date = "Applied on Mar 5, 2026"
                )
            }
        }
    }
}

@Composable
fun ApplicationStatusCard(clubName: String, status: String, date: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = clubName,
                    color = TextWhite,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = date,
                    color = TextGray,
                    fontSize = 12.sp
                )
            }

            Text(
                text = status,
                color = when (status) {
                    "Approved" -> androidx.compose.ui.graphics.Color(0xFF4CAF50)
                    "Rejected" -> androidx.compose.ui.graphics.Color(0xFFE74C3C)
                    else -> PrimaryBlue
                },
                fontWeight = FontWeight.Bold
            )
        }
    }
}