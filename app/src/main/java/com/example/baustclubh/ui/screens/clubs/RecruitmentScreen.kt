package com.example.baustclubh.ui.screens.clubs

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
fun RecruitmentScreen(navController: NavController, clubName: String = "CSE Programming Club") {
    var reason by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(BackgroundDark).padding(24.dp)) {
        Text("Join $clubName", color = TextWhite, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Spring 2026 Recruitment", color = PrimaryBlue, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Text("Why do you want to join?", color = TextWhite, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = reason,
            onValueChange = { reason = it },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            placeholder = { Text("Write your motivation...", color = TextGray) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = CardBackground,
                focusedBorderColor = PrimaryBlue
            ),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Application Steps", color = TextWhite, fontWeight = FontWeight.Bold)
        ApplicationStep("1. Online Application Submitted", true)
        ApplicationStep("2. Aptitude Test", false)
        ApplicationStep("3. Final Interview", false)

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { /* ফায়ারবেসে অ্যাপ্লিকেশন সেভ করার লজিক */ },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Submit Application", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ApplicationStep(text: String, isDone: Boolean) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        RadioButton(selected = isDone, onClick = null)
        Text(text, color = if (isDone) TextWhite else TextGray, modifier = Modifier.padding(start = 8.dp))
    }
}