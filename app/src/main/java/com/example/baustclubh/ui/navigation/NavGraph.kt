package com.example.baustclubh.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.baustclubh.ui.screens.auth.LoginScreen
import com.example.baustclubh.ui.screens.auth.RegisterScreen
import com.example.baustclubh.ui.screens.dashboard.HomeScreen
import com.example.baustclubh.ui.screens.dashboard.ProfileScreen
import com.example.baustclubh.ui.screens.clubs.ClubListScreen
import com.example.baustclubh.ui.screens.clubs.ClubDetailScreen
import com.example.baustclubh.ui.screens.clubs.RecruitmentScreen
import com.example.baustclubh.ui.screens.clubs.MyApplicationsScreen
import com.example.baustclubh.ui.screens.resources.LibraryScreen
import com.example.baustclubh.ui.screens.resources.QRAttendanceScreen
import com.example.baustclubh.ui.screens.admin.AdminDashboard
import com.example.baustclubh.ui.screens.admin.PendingApplicationsScreen
import com.example.baustclubh.ui.screens.admin.ManageClubsScreen
import com.example.baustclubh.ui.screens.admin.ManageMembersScreen
import com.example.baustclubh.ui.screens.admin.AddEventScreen
import com.example.baustclubh.ui.screens.alumni.AlumniScreen

@Composable
fun BAUSTNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // Auth Screens
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }

        // Dashboard Screens
        composable("home") { HomeScreen(navController) }
        composable("profile") { ProfileScreen(navController) }

        // Club Screens
        composable("club_list") { ClubListScreen(navController) }
        composable("club_detail/{clubName}") { backStackEntry ->
            val clubName = backStackEntry.arguments?.getString("clubName") ?: "Club"
            ClubDetailScreen(navController, clubName)
        }
        composable("recruitment/{clubName}") { backStackEntry ->
            val clubName = backStackEntry.arguments?.getString("clubName") ?: "Club"
            RecruitmentScreen(navController, clubName)
        }
        composable("my_applications") { MyApplicationsScreen(navController) }

        // Alumni Screen
        composable("alumni") { AlumniScreen(navController) }

        // Admin Screens
        composable("admin_dashboard") { AdminDashboard(navController) }
        composable("admin_applications") { PendingApplicationsScreen(navController) }
        composable("admin_clubs") { ManageClubsScreen(navController) }
        composable("admin_members") { ManageMembersScreen(navController) }
        composable("admin_events") { AddEventScreen(navController) }

        // Resource Screens
        composable("library") { LibraryScreen(navController) }
        composable("qr_scan") { QRAttendanceScreen(navController) }
    }
}