//package com.example.baustclubh.ui.screens.admin
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.baustclubh.ui.theme.*
//
//@Composable
//fun PendingApplicationsScreen(navController: NavController) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(BackgroundDark)
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Pending Applications",
//            color = TextWhite,
//            fontSize = 22.sp,
//            fontWeight = FontWeight.Bold
//        )
//
//        Text(
//            text = "Approve or reject club join requests",
//            color = TextGray,
//            fontSize = 14.sp
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        LazyColumn(
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            item {
//                ApplicationCard(
//                    name = "Rakib Hasan",
//                    studentId = "202410001",
//                    clubName = "Programming Club",
//                    reason = "I want to improve my coding skills and participate in competitive programming."
//                )
//            }
//            item {
//                ApplicationCard(
//                    name = "Sadia Rahman",
//                    studentId = "202410002",
//                    clubName = "Robotics Club",
//                    reason = "I have experience with Arduino and want to build robots."
//                )
//            }
//            item {
//                ApplicationCard(
//                    name = "Tanvir Ahmed",
//                    studentId = "202410003",
//                    clubName = "Debate Club",
//                    reason = "I want to improve my public speaking and debating skills."
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun ApplicationCard(
//    name: String,
//    studentId: String,
//    clubName: String,
//    reason: String
//) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(containerColor = CardBackground),
//        shape = RoundedCornerShape(12.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column {
//                    Text(
//                        text = name,
//                        color = TextWhite,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Text(
//                        text = studentId,
//                        color = TextGray,
//                        fontSize = 12.sp
//                    )
//                    Text(
//                        text = "Club: $clubName",
//                        color = PrimaryBlue,
//                        fontSize = 12.sp
//                    )
//                }
//
//                Row(
//                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    Button(
//                        onClick = { /* Approve logic */ },
//                        colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color(0xFF4CAF50)),
//                        shape = RoundedCornerShape(8.dp)
//                    ) {
//                        Text(
//                            text = "Approve",
//                            color = TextWhite,
//                            fontSize = 12.sp
//                        )
//                    }
//
//                    Button(
//                        onClick = { /* Reject logic */ },
//                        colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color(0xFFE74C3C)),
//                        shape = RoundedCornerShape(8.dp)
//                    ) {
//                        Text(
//                            text = "Reject",
//                            color = TextWhite,
//                            fontSize = 12.sp
//                        )
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = reason,
//                color = TextGray,
//                fontSize = 12.sp
//            )
//        }
//    }
//}


package com.example.baustclubh.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baustclubh.ui.theme.*
import com.example.baustclubh.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendingApplicationsScreen(navController: NavController, authViewModel: AuthViewModel) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val role = currentUser?.role ?: ""

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recruitment Requests", color = TextWhite) },
                colors = TopAppBarDefaults.topAppBarColors(BackgroundDark),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Text("←", color = TextWhite, fontSize = 20.sp)
                    }
                }
            )
        },
        containerColor = BackgroundDark
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            // ফিল্টার স্ট্যাটাস টেক্সট
            val statusLabel = when (role) {
                "super_admin" -> "Viewing All Applications"
                "dept_admin" -> "Applications for ${currentUser?.managedDept} Dept"
                "club_admin" -> "Applications for ${currentUser?.managedClub}"
                else -> ""
            }

            Text(text = statusLabel, color = PrimaryBlue, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // ডামি অ্যাপ্লিকেশন লিস্ট
                items(listOf("Rahat - Robotics Club", "Siam - Computer Club")) { app ->
                    ApplicationCard(app)
                }
            }
        }
    }
}

@Composable
fun ApplicationCard(info: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = info, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(text = "Applied on: 24 April, 2026", color = TextGray, fontSize = 11.sp)

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { /* Approve logic */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2ECC71)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Approve", color = Color.White, fontSize = 12.sp)
                }

                Button(
                    onClick = { /* Reject logic */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE74C3C)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Reject", color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}