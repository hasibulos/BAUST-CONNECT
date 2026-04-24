package com.example.baustclubh.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baustclubh.ui.theme.*

@Composable
fun AddEventScreen(navController: NavController) {
    var eventName by remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf("") }
    var eventTime by remember { mutableStateOf("") }
    var eventVenue by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(16.dp)
    ) {
        Text(
            text = "Create New Event",
            color = TextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = eventName,
            onValueChange = { eventName = it },
            label = { Text("Event Name", color = TextGray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = CardBackground,
                focusedBorderColor = PrimaryBlue
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = eventDate,
            onValueChange = { eventDate = it },
            label = { Text("Date (e.g., Mar 25, 2026)", color = TextGray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = CardBackground,
                focusedBorderColor = PrimaryBlue
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = eventTime,
            onValueChange = { eventTime = it },
            label = { Text("Time (e.g., 10:00 AM)", color = TextGray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = CardBackground,
                focusedBorderColor = PrimaryBlue
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = eventVenue,
            onValueChange = { eventVenue = it },
            label = { Text("Venue", color = TextGray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = CardBackground,
                focusedBorderColor = PrimaryBlue
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = eventDescription,
            onValueChange = { eventDescription = it },
            label = { Text("Description", color = TextGray) },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = CardBackground,
                focusedBorderColor = PrimaryBlue
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("admin_dashboard") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Create Event", fontWeight = FontWeight.Bold, color = TextWhite)
        }
    }
}