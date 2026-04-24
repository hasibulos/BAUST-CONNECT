package com.example.baustclubh.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baustclubh.ui.theme.*

@Composable
fun BottomNavBar(navController: NavController) {
    NavigationBar(
        containerColor = CardBackground,
        tonalElevation = 0.dp
    ) {
        // Home Button
        NavigationBarItem(
            icon = { Text("🏠", fontSize = 22.sp) },
            label = { Text("Home", fontSize = 11.sp) },
            selected = false,
            onClick = {
                navController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                    launchSingleTop = true
                }
            }
        )

        // Clubs Button
        NavigationBarItem(
            icon = { Text("👥", fontSize = 22.sp) },
            label = { Text("Clubs", fontSize = 11.sp) },
            selected = false,
            onClick = {
                navController.navigate("club_list") {
                    launchSingleTop = true
                }
            }
        )

        // Library Button
        NavigationBarItem(
            icon = { Text("📚", fontSize = 22.sp) },
            label = { Text("Library", fontSize = 11.sp) },
            selected = false,
            onClick = {
                navController.navigate("library") {
                    launchSingleTop = true
                }
            }
        )

        // Alumni Button
        NavigationBarItem(
            icon = { Text("🎓", fontSize = 22.sp) },
            label = { Text("Alumni", fontSize = 11.sp) },
            selected = false,
            onClick = {
                navController.navigate("alumni") {
                    launchSingleTop = true
                }
            }
        )
    }
}