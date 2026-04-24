package com.example.baustclubh.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.baustclubh.viewmodel.LoginState
import com.example.baustclubh.ui.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val loginState by authViewModel.loginState.collectAsState()

    val user = currentUser ?: if (loginState is LoginState.Success) {
        (loginState as LoginState.Success).user
    } else {
        null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Profile",
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user?.name?.take(1)?.uppercase() ?: "U",
                    color = TextWhite,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = user?.name ?: "User Name",
                color = TextWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "ID: ${user?.studentId ?: "N/A"}",
                color = TextGray,
                fontSize = 14.sp
            )

            Text(
                text = user?.email ?: "Email not set",
                color = PrimaryBlue,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            ProfileInfoCard(icon = "🎓", title = "Department", value = user?.department ?: "Not set")
            ProfileInfoCard(icon = "📚", title = "Batch", value = user?.batch ?: "Not set")
            ProfileInfoCard(icon = "👥", title = "Clubs Joined", value = user?.registeredClubs?.size?.toString() ?: "0")
            ProfileInfoCard(icon = "📅", title = "Member Since", value = user?.joinDate?.ifEmpty { "2026" } ?: "2026")

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                        popUpTo("profile") { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE74C3C)),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout", color = TextWhite, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ProfileInfoCard(icon: String, title: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = icon, fontSize = 20.sp)
                Text(text = title, color = TextGray, fontSize = 14.sp)
            }
            Text(
                text = value,
                color = TextWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}