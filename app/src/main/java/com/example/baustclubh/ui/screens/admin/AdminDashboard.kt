////package com.example.baustclubh.ui.screens.admin
////
////import androidx.compose.foundation.layout.*
////import androidx.compose.foundation.lazy.LazyColumn
////import androidx.compose.foundation.shape.RoundedCornerShape
////import androidx.compose.material.icons.Icons
////import androidx.compose.material.icons.filled.Logout
////import androidx.compose.material3.*
////import androidx.compose.runtime.Composable
////import androidx.compose.runtime.collectAsState
////import androidx.compose.runtime.getValue
////import androidx.compose.ui.Alignment
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.graphics.Color
////import androidx.compose.ui.text.font.FontWeight
////import androidx.compose.ui.unit.dp
////import androidx.compose.ui.unit.sp
////import androidx.navigation.NavController
////import com.example.baustclubh.ui.theme.*
////import com.example.baustclubh.viewmodel.AuthViewModel
////
////@Composable
////fun AdminDashboard(navController: NavController, authViewModel: AuthViewModel) {
////    val currentUser by authViewModel.currentUser.collectAsState()
////    val role = currentUser?.role ?: "student"
////    val userDept = currentUser?.department ?: "Dept"
////
////    Scaffold(
////        modifier = Modifier.fillMaxSize(),
////        // থিম অনুযায়ী ব্যাকগ্রাউন্ড কালার অটোমেটিক ডাইনামিক হবে
////        containerColor = MaterialTheme.colorScheme.background
////    ) { innerPadding ->
////        Column(
////            modifier = Modifier
////                .fillMaxSize()
////                .padding(innerPadding)
////                .padding(horizontal = 16.dp, vertical = 8.dp)
////        ) {
////            // Top Header with Logout Button
////            Row(
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .padding(vertical = 8.dp),
////                horizontalArrangement = Arrangement.SpaceBetween,
////                verticalAlignment = Alignment.CenterVertically
////            ) {
////                Column {
////                    Text(
////                        text = "Admin Dashboard",
////                        color = MaterialTheme.colorScheme.onBackground, // থিম টেক্সট
////                        fontSize = 26.sp,
////                        fontWeight = FontWeight.Bold
////                    )
////                    Text(
////                        text = "Logged in as: ${role.replace("_", " ").uppercase()}",
////                        color = MaterialTheme.colorScheme.primary, // থিম প্রাইমারি ব্লু
////                        fontSize = 14.sp
////                    )
////                }
////
////                IconButton(onClick = {
////                    authViewModel.logout()
////                    navController.navigate("login") {
////                        popUpTo(0)
////                    }
////                }) {
////                    Icon(
////                        imageVector = Icons.Default.Logout,
////                        contentDescription = "Logout",
////                        tint = Color(0xFFFF5252)
////                    )
////                }
////            }
////
////            if (role == "dept_admin") {
////                Text(
////                    text = "Managed Dept: $userDept",
////                    color = MaterialTheme.colorScheme.onSurfaceVariant,
////                    fontSize = 12.sp,
////                    modifier = Modifier.padding(bottom = 8.dp)
////                )
////            }
////
////            Spacer(modifier = Modifier.height(16.dp))
////
////            LazyColumn(
////                verticalArrangement = Arrangement.spacedBy(12.dp),
////                contentPadding = PaddingValues(bottom = 24.dp)
////            ) {
////                if (role == "super_admin") {
////                    item {
////                        AdminCard("Manage Dept Admins", "Create Dept Admin Accounts & Passwords", "🔑") {
////                            // 🔥 ক্র্যাশ ফিক্স: BAUSTNavGraph এর সাথে ম্যাচ করে রুট "add_admin" করা হলো
////                            navController.navigate("add_admin")
////                        }
////                    }
////                }
////
////                if (role == "dept_admin") {
////                    item {
////                        AdminCard("Manage Club Moderators", "Create Club Admin Accounts for $userDept", "🛠️") {
////                            // ডিপার্টমেন্টাল এডমিনও একই স্ক্রিন ব্যবহার করে ক্লাব এডমিন ক্রিয়েট করতে পারবে
////                            navController.navigate("add_admin")
////                        }
////                    }
////                }
////
////                if (role == "super_admin" || role == "dept_admin") {
////                    item {
////                        AdminCard("Manage Clubs", "Add or Remove University Clubs", "👥") {
////                            navController.navigate("admin_clubs")
////                        }
////                    }
////                }
////
////                item {
////                    AdminCard("Club Events", "Create and Edit Club Activities", "📅") {
////                        navController.navigate("admin_events")
////                    }
////                }
////
////                item {
////                    AdminCard("Pending Applications", "Review Student Join Requests", "📝") {
////                        navController.navigate("admin_applications")
////                    }
////                }
////
////                item {
////                    AdminCard("QR Attendance", "Verify Event Participation", "📷") {
////                        navController.navigate("qr_scan")
////                    }
////                }
////
////                // --- View Site অপশন ---
////                item {
////                    AdminCard("View Site", "Switch to student view to see club hub", "🌐") {
////                        navController.navigate("home")
////                    }
////                }
////            }
////        }
////    }
////}
////
////@OptIn(ExperimentalMaterial3Api::class)
////@Composable
////fun AdminCard(title: String, description: String, icon: String, onClick: () -> Unit) {
////    Card(
////        modifier = Modifier.fillMaxWidth(),
////        // থিম অনুযায়ী কার্ডের কালার লাইট/ডার্ক মোডে অটো চেঞ্জ হবে
////        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
////        shape = RoundedCornerShape(16.dp),
////        onClick = onClick
////    ) {
////        Row(modifier = Modifier.padding(20.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
////            Text(text = icon, fontSize = 32.sp)
////            Column {
////                Text(text = title, color = MaterialTheme.colorScheme.onBackground, fontSize = 17.sp, fontWeight = FontWeight.Bold)
////                Text(text = description, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
////            }
////        }
////    }
////}
//
//
//
//
////
////package com.example.baustclubh.ui.screens.admin
////
////import androidx.compose.foundation.layout.*
////import androidx.compose.foundation.lazy.LazyColumn
////import androidx.compose.foundation.shape.RoundedCornerShape
////import androidx.compose.material.icons.Icons
////import androidx.compose.material.icons.filled.Logout
////import androidx.compose.material3.*
////import androidx.compose.runtime.Composable
////import androidx.compose.runtime.collectAsState
////import androidx.compose.runtime.getValue
////import androidx.compose.ui.Alignment
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.graphics.Color
////import androidx.compose.ui.text.font.FontWeight
////import androidx.compose.ui.unit.dp
////import androidx.compose.ui.unit.sp
////import androidx.navigation.NavController
////import com.example.baustclubh.ui.theme.*
////import com.example.baustclubh.viewmodel.AuthViewModel
////
////@Composable
////fun AdminDashboard(navController: NavController, authViewModel: AuthViewModel) {
////    val currentUser by authViewModel.currentUser.collectAsState()
////
////    // 🛠️ ফিক্সড চেক: LoginScreen থেকে পাঠানো forced_role রিড করবে, না থাকলে ফায়ারবেস বা ডিফল্ট সুপারঅ্যাডমিন রোল সেট করবে
////    val forcedRole = navController.previousBackStackEntry?.savedStateHandle?.get<String>("forced_role")
////    val role = forcedRole ?: currentUser?.role ?: "super_admin"
////    val userDept = currentUser?.department ?: "Dept"
////
////    Scaffold(
////        modifier = Modifier.fillMaxSize(),
////        containerColor = MaterialTheme.colorScheme.background
////    ) { innerPadding ->
////        Column(
////            modifier = Modifier
////                .fillMaxSize()
////                .padding(innerPadding)
////                .padding(horizontal = 16.dp, vertical = 8.dp)
////        ) {
////            // Top Header with Logout Button
////            Row(
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .padding(vertical = 8.dp),
////                horizontalArrangement = Arrangement.SpaceBetween,
////                verticalAlignment = Alignment.CenterVertically
////            ) {
////                Column {
////                    Text(
////                        text = "Admin Dashboard",
////                        color = MaterialTheme.colorScheme.onBackground,
////                        fontSize = 26.sp,
////                        fontWeight = FontWeight.Bold
////                    )
////                    Text(
////                        text = "Logged in as: ${role.replace("_", " ").uppercase()}",
////                        color = MaterialTheme.colorScheme.primary,
////                        fontSize = 14.sp
////                    )
////                }
////
////                IconButton(onClick = {
////                    navController.previousBackStackEntry?.savedStateHandle?.remove<String>("forced_role")
////                    authViewModel.logout()
////                    navController.navigate("login") {
////                        popUpTo(0)
////                    }
////                }) {
////                    Icon(
////                        imageVector = Icons.Default.Logout,
////                        contentDescription = "Logout",
////                        tint = Color(0xFFFF5252)
////                    )
////                }
////            }
////
////            if (role == "dept_admin") {
////                Text(
////                    text = "Managed Dept: $userDept",
////                    color = MaterialTheme.colorScheme.onSurfaceVariant,
////                    fontSize = 12.sp,
////                    modifier = Modifier.padding(bottom = 8.dp)
////                )
////            }
////
////            Spacer(modifier = Modifier.height(16.dp))
////
////            // --- image_b27803.png এর ফাইল স্ট্রাকচার অনুযায়ী বাটন লিস্ট ---
////            LazyColumn(
////                verticalArrangement = Arrangement.spacedBy(12.dp),
////                contentPadding = PaddingValues(bottom = 24.dp)
////            ) {
////                // ১. AddAdminScreen.kt
////                if (role == "super_admin" || role == "dept_admin") {
////                    item {
////                        val cardTitle = if (role == "super_admin") "Manage Dept Admins" else "Manage Club Moderators"
////                        val cardDesc = if (role == "super_admin") "Create Department Admin Accounts" else "Create Club Admin Accounts for $userDept"
////                        AdminCard(cardTitle, cardDesc, "🔑") {
////                            navController.navigate("add_admin")
////                        }
////                    }
////                }
////
////                // ২. ManageClubsScreen.kt
////                if (role == "super_admin" || role == "dept_admin") {
////                    item {
////                        AdminCard("Manage Clubs", "Add or Remove University Clubs & Hubs", "👥") {
////                            navController.navigate("admin_clubs")
////                        }
////                    }
////                }
////
////                // ৩. ManageEventsScreen.kt
////                item {
////                    AdminCard("Manage Events", "Create, Edit & Publish Club Activities", "📅") {
////                        navController.navigate("admin_events")
////                    }
////                }
////
////                // ৪. PendingApplicationsScreen.kt
////                item {
////                    AdminCard("Pending Applications", "Review and Approve Student Join Requests", "📝") {
////                        navController.navigate("admin_applications")
////                    }
////                }
////
////                // ৫. ManageMembersScreen.kt
////                item {
////                    AdminCard("Manage Members", "View and Manage Registered Club Members", "🎖️") {
////                        navController.navigate("manage_members")
////                    }
////                }
////
////                // 🌐 View Site
////                item {
////                    AdminCard("View Site", "Switch to student view to see club hub", "🌐") {
////                        navController.navigate("home")
////                    }
////                }
////            }
////        }
////    }
////}
////
////@OptIn(ExperimentalMaterial3Api::class)
////@Composable
////fun AdminCard(title: String, description: String, icon: String, onClick: () -> Unit) {
////    Card(
////        modifier = Modifier.fillMaxWidth(),
////        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
////        shape = RoundedCornerShape(16.dp),
////        onClick = onClick
////    ) {
////        Row(modifier = Modifier.padding(20.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
////            Text(text = icon, fontSize = 32.sp)
////            Column {
////                Text(text = title, color = MaterialTheme.colorScheme.onBackground, fontSize = 17.sp, fontWeight = FontWeight.Bold)
////                Text(text = description, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
////            }
////        }
////    }
////}
//
//
//package com.example.baustclubh.ui.screens.admin
//
//import android.content.Context
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Logout
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
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
//    val context = LocalContext.current
//
//    // 💾 লোকাল সেশন রিড করার জন্য SharedPreferences
//    val sharedPreferences = remember { context.getSharedPreferences("baust_club_prefs", Context.MODE_PRIVATE) }
//
//    // 🔄 ১. প্রথমে SharedPreferences থেকে সংরক্ষিত রোল নিবে, না থাকলে ফায়ারবেস বা ডিফল্ট রোল চেক করবে
//    val savedRole = sharedPreferences.getString("user_role", null)
//    val forcedRole = navController.previousBackStackEntry?.savedStateHandle?.get<String>("forced_role")
//    val role = savedRole ?: forcedRole ?: currentUser?.role ?: "super_admin"
//
//    val userDept = currentUser?.department ?: "Dept"
//
//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        containerColor = MaterialTheme.colorScheme.background
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .padding(horizontal = 16.dp, vertical = 8.dp)
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
//                        color = MaterialTheme.colorScheme.onBackground,
//                        fontSize = 26.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Text(
//                        text = "Logged in as: ${role.replace("_", " ").uppercase()}",
//                        color = MaterialTheme.colorScheme.primary,
//                        fontSize = 14.sp
//                    )
//                }
//
//                IconButton(onClick = {
//                    // 📑 লগআউট করার সাথে সাথে লোকাল SharedPreferences মেমোরি সম্পূর্ণ ক্লিয়ার করা হচ্ছে
//                    sharedPreferences.edit().clear().apply()
//
//                    navController.previousBackStackEntry?.savedStateHandle?.remove<String>("forced_role")
//                    authViewModel.logout()
//
//                    // সেশন ক্লিয়ার করে সরাসরি লগইন স্ক্রিনে ব্যাক করবে এবং স্ট্যাক ক্লিয়ার করবে
//                    navController.navigate("login") {
//                        popUpTo(0) { inclusive = true }
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
//                    color = MaterialTheme.colorScheme.onSurfaceVariant,
//                    fontSize = 12.sp,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // --- image_b27803.png এর ফাইল স্ট্রাকচার অনুযায়ী বাটন লিস্ট ---
//            LazyColumn(
//                verticalArrangement = Arrangement.spacedBy(12.dp),
//                contentPadding = PaddingValues(bottom = 24.dp)
//            ) {
//                // ১. AddAdminScreen.kt
//                if (role == "super_admin" || role == "dept_admin") {
//                    item {
//                        val cardTitle = if (role == "super_admin") "Manage Dept Admins" else "Manage Club Moderators"
//                        val cardDesc = if (role == "super_admin") "Create Department Admin Accounts" else "Create Club Admin Accounts for $userDept"
//                        AdminCard(cardTitle, cardDesc, "🔑") {
//                            navController.navigate("add_admin")
//                        }
//                    }
//                }
//
//                // ২. ManageClubsScreen.kt
//                if (role == "super_admin" || role == "dept_admin") {
//                    item {
//                        AdminCard("Manage Clubs", "Add or Remove University Clubs & Hubs", "👥") {
//                            navController.navigate("admin_clubs")
//                        }
//                    }
//                }
//
//                // ৩. ManageEventsScreen.kt
//                item {
//                    AdminCard("Manage Events", "Create, Edit & Publish Club Activities", "📅") {
//                        navController.navigate("admin_events")
//                    }
//                }
//
//                // ৪. PendingApplicationsScreen.kt
//                item {
//                    AdminCard("Pending Applications", "Review and Approve Student Join Requests", "📝") {
//                        navController.navigate("admin_applications")
//                    }
//                }
//
//                // ৫. ManageMembersScreen.kt
//                item {
//                    AdminCard("Manage Members", "View and Manage Registered Club Members", "🎖️") {
//                        navController.navigate("manage_members")
//                    }
//                }
//
//                // 🌐 View Site
//                item {
//                    AdminCard("View Site", "Switch to student view to see club hub", "🌐") {
//                        navController.navigate("home")
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
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
//        shape = RoundedCornerShape(16.dp),
//        onClick = onClick
//    ) {
//        Row(modifier = Modifier.padding(20.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
//            Text(text = icon, fontSize = 32.sp)
//            Column {
//                Text(text = title, color = MaterialTheme.colorScheme.onBackground, fontSize = 17.sp, fontWeight = FontWeight.Bold)
//                Text(text = description, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
//            }
//        }
//    }
//}



package com.example.baustclubh.ui.screens.admin

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baustclubh.ui.theme.*
import com.example.baustclubh.viewmodel.AuthViewModel

@Composable
fun AdminDashboard(navController: NavController, authViewModel: AuthViewModel) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val context = LocalContext.current

    // 💾 লোকাল সেশন রিড করার জন্য SharedPreferences
    val sharedPreferences = remember { context.getSharedPreferences("baust_club_prefs", Context.MODE_PRIVATE) }

    // 🔄 ১. প্রথমে SharedPreferences থেকে সংরক্ষিত রোল নিবে, না থাকলে ফায়ারবেস বা ডিফল্ট রোল চেক করবে
    val savedRole = sharedPreferences.getString("user_role", null)
    val forcedRole = navController.previousBackStackEntry?.savedStateHandle?.get<String>("forced_role")
    val role = savedRole ?: forcedRole ?: currentUser?.role ?: "super_admin"

    val userDept = currentUser?.department ?: "Dept"

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
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
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Logged in as: ${role.replace("_", " ").uppercase()}",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 14.sp
                    )
                }

                IconButton(onClick = {
                    // 📑 লগআউট করার সাথে সাথে লোকাল SharedPreferences মেমোরি সম্পূর্ণ ক্লিয়ার করা হচ্ছে
                    sharedPreferences.edit().clear().apply()

                    navController.previousBackStackEntry?.savedStateHandle?.remove<String>("forced_role")
                    authViewModel.logout()

                    // সেশন ক্লিয়ার করে সরাসরি লগইন স্ক্রিনে ব্যাক করবে এবং স্ট্যাক ক্লিয়ার করবে
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
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
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- বাটন লিস্ট ---
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                // ১. AddAdminScreen.kt
                if (role == "super_admin" || role == "dept_admin") {
                    item {
                        val cardTitle = if (role == "super_admin") "Manage Dept Admins" else "Manage Club Moderators"
                        val cardDesc = if (role == "super_admin") "Create Department Admin Accounts" else "Create Club Admin Accounts for $userDept"
                        AdminCard(cardTitle, cardDesc, "🔑") {
                            navController.navigate("add_admin")
                        }
                    }
                }

                // ২. ManageClubsScreen.kt
                if (role == "super_admin" || role == "dept_admin") {
                    item {
                        AdminCard("Manage Clubs", "Add or Remove University Clubs & Hubs", "👥") {
                            navController.navigate("admin_clubs")
                        }
                    }
                }

                // ৩. ManageEventsScreen.kt
                item {
                    AdminCard("Manage Events", "Create, Edit & Publish Club Activities", "📅") {
                        navController.navigate("admin_events")
                    }
                }

                // ৪. PendingApplicationsScreen.kt
                item {
                    AdminCard("Pending Applications", "Review and Approve Student Join Requests", "📝") {
                        navController.navigate("admin_applications")
                    }
                }

                // ৫. ManageMembersScreen.kt
                item {
                    AdminCard("Manage Members", "View and Manage Registered Club Members", "🎖️") {
                        navController.navigate("manage_members")
                    }
                }

                // 🔥 ৬. Manage Alumni Network (নতুন যুক্ত করা হয়েছে)
                if (role == "super_admin" || role == "dept_admin") {
                    item {
                        AdminCard(
                            title = "Manage Alumni",
                            description = "Add, Edit or Delete BAUST Alumni Profiles",
                            icon = "🎓"
                        ) {
                            navController.navigate("manage_alumni")
                        }
                    }
                }

                // 🌐 View Site
                item {
                    AdminCard("View Site", "Switch to student view to see club hub", "🌐") {
                        navController.navigate("home")
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Row(modifier = Modifier.padding(20.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(text = icon, fontSize = 32.sp)
            Column {
                Text(text = title, color = MaterialTheme.colorScheme.onBackground, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                Text(text = description, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
            }
        }
    }
}