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
import com.example.baustclubh.viewmodel.AuthViewModel
import com.example.baustclubh.viewmodel.ClubViewModel
import com.example.baustclubh.ui.screens.alumni.AlumniProfileScreen
import com.example.baustclubh.data.model.Event // মডেলের জন্য
import com.example.baustclubh.ui.screens.clubs.EventListScreen // সঠিক ডিরেক্টরি ইম্পোর্ট

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

        // 🎓 অ্যালমনাই মেইন স্ক্রিন রুট (প্যারামিটার সহ ফিক্সড করা হয়েছে)
        composable("alumni") {
            AlumniScreen(navController = navController, authViewModel = authViewModel)
        }

        composable("library") { LibraryScreen(navController) }

        // --- Admin Section ---
        composable("admin_dashboard") { AdminDashboard(navController, authViewModel) }
        composable("admin_applications") { PendingApplicationsScreen(navController, authViewModel) }
        composable("admin_clubs") { ManageClubsScreen(navController, authViewModel) }
        composable("admin_members") { ManageMembersScreen(navController, authViewModel) }
        composable("admin_events") { ManageEventsScreen(navController, authViewModel) }

        // নতুন রুট: সুপার অ্যাডমিন এই রুট ব্যবহার করে ডিপার্টমেন্ট/ক্লাব অ্যাডমিন ক্রিয়েট করবেন
        composable("add_admin") { AddAdminScreen(navController) }

        // ⚙️ অ্যাডমিনদের জন্য অ্যালমনাই ম্যানেজমেন্ট (Add, Edit, Delete) স্ক্রিন রুট
        composable("manage_alumni") { ManageAlumniScreen(navController = navController) }

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

        // 🛠️ ফিক্সড করা রুট: এখানে নিখুঁতভাবে {icon} আর্গুমেন্ট রিসিভ ও পাস করা হয়েছে
        composable(
            route = "alumni_profile/{name}/{batch}/{position}/{icon}/{email}/{linkedin}/{facebook}/{bio}"
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val batch = backStackEntry.arguments?.getString("batch") ?: ""
            val position = backStackEntry.arguments?.getString("position") ?: ""
            val icon = backStackEntry.arguments?.getString("icon") ?: "" // 👈 আইকন ডাটা রিসিভ
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val linkedin = backStackEntry.arguments?.getString("linkedin") ?: ""
            val facebook = backStackEntry.arguments?.getString("facebook") ?: ""
            val bio = backStackEntry.arguments?.getString("bio") ?: ""

            // 📍 ২০২ নম্বর লাইনের কাঙ্ক্ষিত সমাধান (সব আর্গুমেন্ট সফলভাবে পাসড)
            AlumniProfileScreen(
                navController = navController,
                name = name,
                batch = batch,
                position = position,
                icon = icon, // 👈 'icon' প্যারামিটারটি যুক্ত করা হলো
                email = email,
                linkedin = linkedin,
                facebook = facebook,
                bio = bio
            )
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