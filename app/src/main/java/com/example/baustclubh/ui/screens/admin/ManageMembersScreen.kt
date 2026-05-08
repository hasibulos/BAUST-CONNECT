package com.example.baustclubh.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.baustclubh.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageMembersScreen(navController: NavController, authViewModel: AuthViewModel) {

    // ফিক্স ১: সরাসরি বুলিয়ান চেক করা হচ্ছে যাতে টাইপ মিসম্যাচ না হয়
    val currentUserState by authViewModel.currentUser.collectAsState()
    val isAdmin = currentUserState?.role == "super_admin"

    if (!isAdmin) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Access Denied: Super Admin Only",
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("User Role Management", color = TextWhite, fontSize = 18.sp) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = BackgroundDark
                    ),
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Text("←", color = TextWhite, fontSize = 24.sp)
                        }
                    }
                )
            },
            containerColor = BackgroundDark
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("Promote or Revoke Roles", color = TextGray, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(listOf("Student A", "Student B", "Student C")) { member ->
                        MemberRoleCard(name = member)
                    }
                }
            }
        }
    }
}

@Composable
fun MemberRoleCard(name: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = name, color = TextWhite, fontWeight = FontWeight.Bold)
                Text(text = "ID: 2024XXXX", color = TextGray, fontSize = 12.sp)
            }
            Button(
                onClick = { /* Open Dialog */ },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text("Change Role", fontSize = 12.sp)
            }
        }
    }
}