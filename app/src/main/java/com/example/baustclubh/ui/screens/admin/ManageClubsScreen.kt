package com.example.baustclubh.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baustclubh.ui.theme.*

@Composable
fun ManageClubsScreen(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    var clubName by remember { mutableStateOf("") }
    var clubDescription by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Manage Clubs",
                color = TextWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("+ Add Club", color = TextWhite)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { ManageClubCard("CSE Programming Club", "Technical Club", "156 Members") }
            item { ManageClubCard("BAUST Robotics Club", "Robotics", "84 Members") }
            item { ManageClubCard("Debate Club", "Debating", "42 Members") }
            item { ManageClubCard("Cultural Club", "Cultural Activities", "65 Members") }
        }
    }

    // Add Club Dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Add New Club", color = TextWhite) },
            text = {
                Column {
                    OutlinedTextField(
                        value = clubName,
                        onValueChange = { clubName = it },
                        label = { Text("Club Name", color = TextGray) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = CardBackground,
                            focusedBorderColor = PrimaryBlue
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = clubDescription,
                        onValueChange = { clubDescription = it },
                        label = { Text("Description", color = TextGray) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = CardBackground,
                            focusedBorderColor = PrimaryBlue
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    // Save club logic
                    showDialog = false
                }) {
                    Text("Add", color = PrimaryBlue)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel", color = TextGray)
                }
            },
            containerColor = CardBackground
        )
    }
}

@Composable
fun ManageClubCard(name: String, type: String, members: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(name, color = TextWhite, fontWeight = FontWeight.Bold)
                Text(type, color = TextGray, fontSize = 12.sp)
                Text("$members members", color = PrimaryBlue, fontSize = 11.sp)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = { /* Edit */ }) {
                    Text("✏️", fontSize = 18.sp)
                }
                IconButton(onClick = { /* Delete */ }) {
                    Text("🗑️", fontSize = 18.sp)
                }
            }
        }
    }
}