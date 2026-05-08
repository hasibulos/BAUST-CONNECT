//package com.example.baustclubh.ui.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import androidx.navigation.NavType
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.navArgument
//import com.example.baustclubh.ui.screens.auth.LoginScreen
//import com.example.baustclubh.ui.screens.auth.RegisterScreen
//import com.example.baustclubh.ui.screens.dashboard.HomeScreen
//import com.example.baustclubh.ui.screens.dashboard.ProfileScreen
//import com.example.baustclubh.ui.screens.clubs.ClubListScreen
//import com.example.baustclubh.ui.screens.clubs.ClubDetailScreen
//import com.example.baustclubh.ui.screens.clubs.MyApplicationsScreen
//import com.example.baustclubh.ui.screens.clubs.RecruitmentScreen
//import com.example.baustclubh.ui.screens.resources.LibraryScreen
//import com.example.baustclubh.ui.screens.resources.QRAttendanceScreen
//import com.example.baustclubh.ui.screens.admin.AdminDashboard
//import com.example.baustclubh.ui.screens.admin.PendingApplicationsScreen
//import com.example.baustclubh.ui.screens.admin.ManageClubsScreen
//import com.example.baustclubh.ui.screens.admin.ManageMembersScreen
//import com.example.baustclubh.ui.screens.admin.ManageEventsScreen
//import com.example.baustclubh.ui.screens.alumni.AlumniScreen
//import com.example.baustclubh.ui.screens.events.EventDetailScreen
//import com.example.baustclubh.ui.screens.events.EventListScreen
//import com.example.baustclubh.viewmodel.AuthViewModel
//import com.example.baustclubh.viewmodel.ClubViewModel
//
//@Composable
//fun BAUSTNavGraph(navController: NavHostController) {
//    val authViewModel: AuthViewModel = viewModel()
//    val clubViewModel: ClubViewModel = viewModel()
//
//    NavHost(
//        navController = navController,
//        startDestination = "login"
//    ) {
//        composable("login") { LoginScreen(navController, authViewModel) }
//        composable("register") { RegisterScreen(navController, authViewModel) }
//        composable("home") { HomeScreen(navController, authViewModel) }
//        composable("profile") { ProfileScreen(navController, authViewModel) }
//        composable("admin_dashboard") { AdminDashboard(navController, authViewModel) }
//        composable("admin_applications") { PendingApplicationsScreen(navController, authViewModel) }
//        composable("admin_clubs") { ManageClubsScreen(navController, authViewModel) }
//        composable("admin_members") { ManageMembersScreen(navController, authViewModel) }
//        composable("admin_events") { ManageEventsScreen(navController, authViewModel) }
//        composable("club_list") { ClubListScreen(navController, clubViewModel) }
//
//        // --- Club Detail Route (Fix করা হয়েছে) ---
//        composable(
//            route = "club_details/{clubId}", // ID দিয়ে ডাটা খোঁজা সহজ
//            arguments = listOf(navArgument("clubId") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val id = backStackEntry.arguments?.getString("clubId")
//            // এখানে clubViewModel পাস করা হয়েছে যাতে ডিটেইলস স্ক্রিন ডাটা পায়
//            ClubDetailScreen(
//                navController = navController,
//                clubId = id,
//                clubViewModel = clubViewModel
//            )
//        }
//        // ইভেন্ট লিস্ট দেখার জন্য
//        composable("event_list") {
//            EventListScreen(navController)
//        }
//
//// ইভেন্ট ডিটেইলস দেখার জন্য
//        composable(
//            route = "event_details/{eventId}",
//            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val id = backStackEntry.arguments?.getString("eventId") ?: ""
//            EventDetailScreen(navController = navController, eventId = id)
//        }
//
//        composable(
//            route = "recruitment/{clubName}",
//            arguments = listOf(navArgument("clubName") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val name = backStackEntry.arguments?.getString("clubName") ?: "Club"
//            RecruitmentScreen(navController, name)
//        }
//
//        composable("my_applications") { MyApplicationsScreen(navController) }
//        composable("alumni") { AlumniScreen(navController) }
//        composable("library") { LibraryScreen(navController) }
//        composable("qr_scan") { QRAttendanceScreen(navController) }
//    }
//}




package com.example.baustclubh.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.baustclubh.ui.screens.auth.LoginScreen
import com.example.baustclubh.ui.screens.auth.RegisterScreen
import com.example.baustclubh.ui.screens.dashboard.HomeScreen
import com.example.baustclubh.ui.screens.dashboard.ProfileScreen
import com.example.baustclubh.ui.screens.clubs.*
import com.example.baustclubh.ui.screens.resources.LibraryScreen
import com.example.baustclubh.ui.screens.resources.QRAttendanceScreen
import com.example.baustclubh.ui.screens.admin.*
import com.example.baustclubh.ui.screens.alumni.AlumniScreen
import com.example.baustclubh.ui.screens.events.EventDetailScreen
import com.example.baustclubh.ui.screens.events.EventListScreen
import com.example.baustclubh.viewmodel.AuthViewModel
import com.example.baustclubh.viewmodel.ClubViewModel

@Composable
fun BAUSTNavGraph(navController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()
    val clubViewModel: ClubViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // --- Authentication ---
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("register") { RegisterScreen(navController, authViewModel) }

        // --- Core Dashboard ---
        composable("home") { HomeScreen(navController, authViewModel) }
        composable("profile") { ProfileScreen(navController, authViewModel) }
        composable("alumni") { AlumniScreen(navController) }
        composable("library") { LibraryScreen(navController) }

        // --- Admin Section ---
        composable("admin_dashboard") { AdminDashboard(navController, authViewModel) }
        composable("admin_applications") { PendingApplicationsScreen(navController, authViewModel) }
        composable("admin_clubs") { ManageClubsScreen(navController, authViewModel) }
        composable("admin_members") { ManageMembersScreen(navController, authViewModel) }
        composable("admin_events") { ManageEventsScreen(navController, authViewModel) }

        // --- Clubs Section ---
        composable("club_list") { ClubListScreen(navController, clubViewModel) }
        composable(
            route = "club_details/{clubId}",
            arguments = listOf(navArgument("clubId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("clubId")
            ClubDetailScreen(navController, id, clubViewModel)
        }
        composable("my_applications") { MyApplicationsScreen(navController) }

        // --- Events Section (Updated Flow) ---
        // ১. ইভেন্ট লিস্ট (হোম থেকে এখানে আসবে)
        composable("event_list") {
            EventListScreen(navController)
        }

        // ২. ইভেন্ট ডিটেইলস (ইভেন্ট লিস্ট থেকে এখানে আসবে)
        composable(
            route = "event_details/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("eventId") ?: ""
            EventDetailScreen(navController = navController, eventId = id)
        }

        // ৩. কিউআর অ্যাটেনডেন্স (ইভেন্ট ডিটেইলস থেকে এখানে আসবে)
        composable("qr_scan") {
            QRAttendanceScreen(navController)
        }

        // --- Recruitment ---
        composable(
            route = "recruitment/{clubName}",
            arguments = listOf(navArgument("clubName") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("clubName") ?: "Club"
            RecruitmentScreen(navController, name)
        }
    }
}