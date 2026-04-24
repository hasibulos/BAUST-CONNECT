package com.example.baustclubh.ui.screens.resources

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baustclubh.ui.theme.*

@Composable
fun QRAttendanceScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "QR Attendance",
            color = TextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Scan QR Code to Mark Attendance",
            color = TextGray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

        // QR Scanner Placeholder
        Box(
            modifier = Modifier
                .size(250.dp)
                .background(CardBackground, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "📷 QR Scanner",
                color = PrimaryBlue,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { /* Handle scan */ },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Start Scanning", fontWeight = FontWeight.Bold)
        }
    }
}