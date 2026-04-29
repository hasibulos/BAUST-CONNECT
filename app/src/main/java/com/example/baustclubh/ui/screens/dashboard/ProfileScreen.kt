//package com.example.baustclubh.ui.screens.dashboard
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import com.example.baustclubh.ui.theme.*
//import com.example.baustclubh.viewmodel.AuthViewModel
//import com.example.baustclubh.viewmodel.LoginState
//import com.example.baustclubh.ui.components.BottomNavBar
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProfileScreen(
//    navController: NavController,
//    authViewModel: AuthViewModel = viewModel()
//) {
//    val currentUser by authViewModel.currentUser.collectAsState()
//    val loginState by authViewModel.loginState.collectAsState()
//
//    val user = currentUser ?: if (loginState is LoginState.Success) {
//        (loginState as LoginState.Success).user
//    } else {
//        null
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "My Profile",
//                        color = TextWhite,
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Medium
//                    )
//                },
//                navigationIcon = {
//                    IconButton(onClick = { navController.navigateUp() }) {
//                        Text(text = "←", color = TextWhite, fontSize = 24.sp)
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundDark)
//            )
//        },
//        bottomBar = { BottomNavBar(navController) },
//        containerColor = BackgroundDark
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(24.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            // Profile Image
//            Box(
//                modifier = Modifier
//                    .size(100.dp)
//                    .clip(CircleShape)
//                    .background(PrimaryBlue),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = user?.name?.take(1)?.uppercase() ?: "U",
//                    color = TextWhite,
//                    fontSize = 40.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text(
//                text = user?.name ?: "User Name",
//                color = TextWhite,
//                fontSize = 22.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            Text(
//                text = "ID: ${user?.studentId ?: "N/A"}",
//                color = TextGray,
//                fontSize = 14.sp
//            )
//
//            Text(
//                text = user?.email ?: "Email not set",
//                color = PrimaryBlue,
//                fontSize = 13.sp
//            )
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            ProfileInfoCard(icon = "🎓", title = "Department", value = user?.department ?: "Not set")
//            ProfileInfoCard(icon = "📚", title = "Batch", value = user?.batch ?: "Not set")
//            ProfileInfoCard(icon = "👥", title = "Clubs Joined", value = user?.registeredClubs?.size?.toString() ?: "0")
//            ProfileInfoCard(icon = "📅", title = "Member Since", value = user?.joinDate?.ifEmpty { "2026" } ?: "2026")
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            Button(
//                onClick = {
//                    authViewModel.logout()
//                    navController.navigate("login") {
//                        popUpTo("home") { inclusive = true }
//                        popUpTo("profile") { inclusive = true }
//                    }
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE74C3C)),
//                shape = MaterialTheme.shapes.medium,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Logout", color = TextWhite, fontWeight = FontWeight.Bold)
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//    }
//}
//
//@Composable
//fun ProfileInfoCard(icon: String, title: String, value: String) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 6.dp),
//        colors = CardDefaults.cardColors(containerColor = CardBackground),
//        shape = MaterialTheme.shapes.medium
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
//                Text(text = icon, fontSize = 20.sp)
//                Text(text = title, color = TextGray, fontSize = 14.sp)
//            }
//            Text(
//                text = value,
//                color = TextWhite,
//                fontWeight = FontWeight.Bold,
//                fontSize = 14.sp
//            )
//        }
//    }
//}


package com.example.baustclubh.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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

    // উপরে-নিচে স্ক্রল করার জন্য স্টেট
    val scrollState = rememberScrollState()

    val user = currentUser ?: if (loginState is LoginState.Success) {
        (loginState as LoginState.Success).user
    } else { null }

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        containerColor = BackgroundDark
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState) // এই লাইনটি স্ক্রিনকে স্ক্রলযোগ্য করবে
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // ১. প্রোফাইল ইমেজ
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user?.name?.take(1)?.uppercase() ?: "H",
                    color = TextWhite, fontSize = 40.sp, fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ২. নাম এবং আইডি
            Text(
                text = user?.name ?: "Hasibul Hasib",
                color = TextWhite, fontSize = 22.sp, fontWeight = FontWeight.Bold
            )

            // ৩. আইডি, ডিপার্টমেন্ট এবং ব্যাচ (এক লাইনে ছোট শর্টকাট হিসেবে)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(text = "ID: ${user?.studentId ?: "080241020510"}", color = PrimaryBlue, fontSize = 12.sp)
                Divider(modifier = Modifier.height(12.dp).width(1.dp), color = TextGray)
                Text(text = user?.department ?: "CSE", color = TextGray, fontSize = 12.sp)
                Divider(modifier = Modifier.height(12.dp).width(1.dp), color = TextGray)
                Text(text = "Batch ${user?.batch ?: "18"}", color = TextGray, fontSize = 12.sp)
            }

            // মেম্বারশিপ ডেট
            Text(
                text = "Member since ${user?.joinDate?.ifEmpty { "2026" } ?: "2026"}",
                color = TextGray.copy(alpha = 0.7f), fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ৪. এডিট এবং শেয়ার বাটন
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { /* Edit Action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    modifier = Modifier.height(38.dp).weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) { Text("Edit Profile", fontSize = 14.sp) }

                OutlinedButton(
                    onClick = { /* Share Action */ },
                    modifier = Modifier.height(38.dp).weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBackground)
                ) { Text("Share", color = TextWhite, fontSize = 14.sp) }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ৫. স্ট্যাট কার্ডস
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem("Clubs", user?.registeredClubs?.size?.toString() ?: "4")
                StatItem("Events", "12")
                StatItem("Certs", "3")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ৬. লিস্ট অপশনস (Alumni Network সহ)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                ProfileMenuLink("My Applications", Icons.Default.Description, Color(0xFF4285F4))
                ProfileMenuLink("Attendance History", Icons.Default.History, Color(0xFF34A853))
                ProfileMenuLink("My Certificates", Icons.Default.EmojiEvents, Color(0xFFFBBC05))

                // নতুন যোগ করা হয়েছে
                ProfileMenuLink("Alumni Network", Icons.Default.School, Color(0xFFE91E63))

                ProfileMenuLink("Notifications", Icons.Default.Notifications, Color(0xFFEA4335))
                ProfileMenuLink("Settings", Icons.Default.Settings, Color(0xFF673AB7))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // লগআউট
            Button(
                onClick = {
                    authViewModel.logout()
                    navController.navigate("login") { popUpTo(0) }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC0392B).copy(alpha = 0.1f)),
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Logout Account", color = Color(0xFFE74C3C), fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Card(
        modifier = Modifier.width(105.dp).height(85.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = value, color = PrimaryBlue, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = label, color = TextGray, fontSize = 12.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileMenuLink(title: String, icon: ImageVector, iconColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(14.dp),
        onClick = { /* Navigate */ }
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, color = TextWhite, fontSize = 15.sp, modifier = Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextGray, modifier = Modifier.size(20.dp))
        }
    }
}