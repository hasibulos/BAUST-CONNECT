package com.example.baustclubh.ui.screens.clubs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.baustclubh.ui.theme.*
import com.example.baustclubh.viewmodel.ClubViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubDetailScreen(navController: NavController, clubId: String?, clubViewModel: ClubViewModel) {
    val clubList by clubViewModel.clubs.collectAsState()
    val club = clubList.find { it.id == clubId }

    if (club == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = PrimaryBlue)
        }
        return
    }

    Scaffold(
        containerColor = BackgroundDark
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // --- ১. Header Section (Cover Image Style) ---
            Box(modifier = Modifier.height(220.dp).fillMaxWidth()) {
                // Cover Placeholder with Gradient
                Box(modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(listOf(PrimaryBlue.copy(0.5f), BackgroundDark))
                ))

                // Back Button
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(16.dp).background(Color.Black.copy(0.3f), CircleShape)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                }

                // Profile Image (Circular Overlay)
                AsyncImage(
                    model = club.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.BottomCenter)
                        .offset(y = 50.dp)
                        .clip(CircleShape)
                        .background(CardBackground)
                        .padding(4.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            // --- ২. Club Info Section ---
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = club.name,
                    color = TextWhite,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${club.department} | ${club.type}",
                    color = PrimaryBlue,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons (Social Media Style)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = { /* Join Logic */ },
                        colors = ButtonDefaults.buttonColors(PrimaryBlue),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Join Club", fontWeight = FontWeight.Bold)
                    }
                    OutlinedButton(
                        onClick = { /* Message Logic */ },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite)
                    ) {
                        Text("Message")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- ৩. Detailed Info Cards ---
            Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoTile(icon = Icons.Default.People, title = "Moderator", value = club.moderator)
                InfoTile(icon = Icons.Default.Email, title = "Official Email", value = club.email)
                InfoTile(icon = Icons.Default.Language, title = "Website", value = club.website)
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun InfoTile(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, color = TextGray, fontSize = 12.sp)
                Text(if(value.isEmpty()) "Not provided" else value, color = TextWhite, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}