package com.example.baustclubh.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.baustclubh.ui.theme.*

@Composable
fun BottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = CardBackground,
        tonalElevation = 0.dp
    ) {
        // ১. Home
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Home, contentDescription = null, modifier = Modifier.size(24.dp)) },
            label = { Text("Home", fontSize = 10.sp) },
            selected = currentRoute == "home",
            onClick = {
                if (currentRoute != "home") {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            },
            colors = navigationBarItemColors()
        )

        // ২. Clubs
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Groups, contentDescription = null, modifier = Modifier.size(24.dp)) },
            label = { Text("Clubs", fontSize = 10.sp) },
            selected = currentRoute == "club_list",
            onClick = {
                if (currentRoute != "club_list") {
                    navController.navigate("club_list") { launchSingleTop = true }
                }
            },
            colors = navigationBarItemColors()
        )

        // ৩. Library
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.MenuBook, contentDescription = null, modifier = Modifier.size(24.dp)) },
            label = { Text("Library", fontSize = 10.sp) },
            selected = currentRoute == "library",
            onClick = {
                if (currentRoute != "library") {
                    navController.navigate("library") { launchSingleTop = true }
                }
            },
            colors = navigationBarItemColors()
        )

        // ৪. Events (স্মুথ নেভিগেশন ফিক্স করা হয়েছে)
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.CalendarMonth, contentDescription = null, modifier = Modifier.size(24.dp)) },
            label = { Text("Events", fontSize = 10.sp) },
            selected = currentRoute == "event_list",
            onClick = {
                if (currentRoute != "event_list") {
                    navController.navigate("event_list") {
                        launchSingleTop = true
                        // popUpTo সরিয়ে দেওয়া হয়েছে যাতে অন্য ট্যাব থেকে আসার সময় স্মুথ ট্রানজিশন হয়
                    }
                }
            },
            colors = navigationBarItemColors()
        )

        // ৫. Profile
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Person, contentDescription = null, modifier = Modifier.size(24.dp)) },
            label = { Text("Profile", fontSize = 10.sp) },
            selected = currentRoute == "profile",
            onClick = {
                if (currentRoute != "profile") {
                    navController.navigate("profile") { launchSingleTop = true }
                }
            },
            colors = navigationBarItemColors()
        )
    }
}

@Composable
fun navigationBarItemColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = PrimaryBlue,
    selectedTextColor = PrimaryBlue,
    unselectedIconColor = TextGray,
    unselectedTextColor = TextGray,
    indicatorColor = Color.Transparent
)