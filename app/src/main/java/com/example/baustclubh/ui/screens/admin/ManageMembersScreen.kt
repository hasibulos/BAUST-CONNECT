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
fun ManageMembersScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(16.dp)
    ) {
        Text(
            text = "All Members",
            color = TextWhite,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Total: 156 members",
            color = TextGray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { MemberCard("Hasibul Hasib", "202410001", "CSE", "Programming Club") }
            item { MemberCard("Rakib Hasan", "202410002", "CSE", "Robotics Club") }
            item { MemberCard("Sadia Rahman", "202410003", "EEE", "Debate Club") }
            item { MemberCard("Tanvir Ahmed", "202410004", "BBA", "Cultural Club") }
        }
    }
}

@Composable
fun MemberCard(name: String, studentId: String, department: String, club: String) {
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
                Text(name, color = TextWhite, fontWeight = FontWeight.Bold)
                Text("ID: $studentId", color = TextGray, fontSize = 12.sp)
                Text(department, color = TextGray, fontSize = 12.sp)
                Text(club, color = PrimaryBlue, fontSize = 12.sp)
            }
            Button(
                onClick = { /* Remove member */ },
                colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color(0xFFE74C3C)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Remove", fontSize = 12.sp, color = TextWhite)
            }
        }
    }
}