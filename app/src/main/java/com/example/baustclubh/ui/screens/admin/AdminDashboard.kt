//package com.example.baustclubh.ui.screens.admin
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Logout
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.baustclubh.ui.theme.*
//import com.example.baustclubh.viewmodel.AuthViewModel
//
//@Composable
//fun AdminDashboard(navController: NavController, authViewModel: AuthViewModel) {
//    val currentUser by authViewModel.currentUser.collectAsState()
//    val role = currentUser?.role ?: "student"
//    val userDept = currentUser?.department ?: "Dept"
//
//    // Scaffold ব্যবহার করলে UI আর স্ট্যাটাস বারের নিচে ঢুকবে না
//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        containerColor = BackgroundDark // আপনার থিমের ব্যাকগ্রাউন্ড কালার
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding) // এটি অটোমেটিক ওপরের গ্যাপ ঠিক করে দিবে
//                .padding(horizontal = 16.dp, vertical = 8.dp) // আপনার কাস্টম প্যাডিং
//        ) {
//            // Top Header with Logout Button
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column {
//                    Text(
//                        text = "Admin Dashboard",
//                        color = TextWhite,
//                        fontSize = 26.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Text(
//                        text = "Logged in as: ${role.replace("_", " ").uppercase()}",
//                        color = PrimaryBlue,
//                        fontSize = 14.sp
//                    )
//                }
//
//                // Logout Button
//                IconButton(onClick = {
//                    authViewModel.logout()
//                    navController.navigate("login") {
//                        popUpTo(0)
//                    }
//                }) {
//                    Icon(
//                        imageVector = Icons.Default.Logout,
//                        contentDescription = "Logout",
//                        tint = Color(0xFFFF5252)
//                    )
//                }
//            }
//
//            if (role == "dept_admin") {
//                Text(
//                    text = "Managed Dept: $userDept",
//                    color = TextGray,
//                    fontSize = 12.sp,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            LazyColumn(
//                verticalArrangement = Arrangement.spacedBy(12.dp),
//                contentPadding = PaddingValues(bottom = 24.dp) // নিচের কার্ডটি যাতে একদম নিচে না লেগে থাকে
//            ) {
//                if (role == "super_admin") {
//                    item {
//                        AdminCard("Manage Dept Admins", "Create Dept Admin Accounts & Passwords", "🔑") {
//                            navController.navigate("manage_dept_admins")
//                        }
//                    }
//                }
//
//                if (role == "dept_admin") {
//                    item {
//                        AdminCard("Manage Club Moderators", "Create Club Admin Accounts for $userDept", "🛠️") {
//                            navController.navigate("manage_club_moderators")
//                        }
//                    }
//                }
//
//                if (role == "super_admin" || role == "dept_admin") {
//                    item {
//                        AdminCard("Manage Clubs", "Add or Remove University Clubs", "👥") {
//                            navController.navigate("admin_clubs")
//                        }
//                    }
//                }
//
//                item {
//                    AdminCard("Club Events", "Create and Edit Club Activities", "📅") {
//                        navController.navigate("admin_events")
//                    }
//                }
//
//                item {
//                    AdminCard("Pending Applications", "Review Student Join Requests", "📝") {
//                        navController.navigate("admin_applications")
//                    }
//                }
//
//                item {
//                    AdminCard("QR Attendance", "Verify Event Participation", "📷") {
//                        navController.navigate("qr_scan")
//                    }
//                }
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AdminCard(title: String, description: String, icon: String, onClick: () -> Unit) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(containerColor = CardBackground),
//        shape = RoundedCornerShape(16.dp),
//        onClick = onClick
//    ) {
//        Row(modifier = Modifier.padding(20.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
//            Text(text = icon, fontSize = 32.sp)
//            Column {
//                Text(text = title, color = TextWhite, fontSize = 17.sp, fontWeight = FontWeight.Bold)
//                Text(text = description, color = TextGray, fontSize = 13.sp)
//            }
//        }
//    }
//}

package com.example.baustclubh.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
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

@Composable
fun AdminDashboard(navController: NavController, authViewModel: AuthViewModel) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val role = currentUser?.role ?: "student"
    val userDept = currentUser?.department ?: "Dept"

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundDark
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Top Header with Logout Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Admin Dashboard",
                        color = TextWhite,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Logged in as: ${role.replace("_", " ").uppercase()}",
                        color = PrimaryBlue,
                        fontSize = 14.sp
                    )
                }

                IconButton(onClick = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Logout",
                        tint = Color(0xFFFF5252)
                    )
                }
            }

            if (role == "dept_admin") {
                Text(
                    text = "Managed Dept: $userDept",
                    color = TextGray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                if (role == "super_admin") {
                    item {
                        AdminCard("Manage Dept Admins", "Create Dept Admin Accounts & Passwords", "🔑") {
                            navController.navigate("manage_dept_admins")
                        }
                    }
                }

                if (role == "dept_admin") {
                    item {
                        AdminCard("Manage Club Moderators", "Create Club Admin Accounts for $userDept", "🛠️") {
                            navController.navigate("manage_club_moderators")
                        }
                    }
                }

                if (role == "super_admin" || role == "dept_admin") {
                    item {
                        AdminCard("Manage Clubs", "Add or Remove University Clubs", "👥") {
                            navController.navigate("admin_clubs")
                        }
                    }
                }

                item {
                    AdminCard("Club Events", "Create and Edit Club Activities", "📅") {
                        navController.navigate("admin_events")
                    }
                }

                item {
                    AdminCard("Pending Applications", "Review Student Join Requests", "📝") {
                        navController.navigate("admin_applications")
                    }
                }

                item {
                    AdminCard("QR Attendance", "Verify Event Participation", "📷") {
                        navController.navigate("qr_scan")
                    }
                }

                // --- নতুন View Site অপশন ---
                item {
                    AdminCard("View Site", "Switch to student view to see club hub", "🌐") {
                        navController.navigate("home") // এটি ইউজার হোম স্ক্রিনে নিয়ে যাবে
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCard(title: String, description: String, icon: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Row(modifier = Modifier.padding(20.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(text = icon, fontSize = 32.sp)
            Column {
                Text(text = title, color = TextWhite, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                Text(text = description, color = TextGray, fontSize = 13.sp)
            }
        }
    }
}