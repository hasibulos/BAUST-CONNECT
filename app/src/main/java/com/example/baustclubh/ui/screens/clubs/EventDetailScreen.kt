package com.example.baustclubh.ui.screens.events

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.baustclubh.data.model.Event
import com.example.baustclubh.ui.theme.*
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(navController: NavController, eventId: String) {
    val db = FirebaseFirestore.getInstance()
    var event by remember { mutableStateOf<Event?>(null) }

    LaunchedEffect(eventId) {
        db.collection("events").document(eventId).get().addOnSuccessListener { doc ->
            event = doc.toObject(Event::class.java)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event Details", color = TextWhite) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundDark)
            )
        },
        containerColor = BackgroundDark
    ) { padding ->
        event?.let { e ->
            Column(
                modifier = Modifier.padding(padding).fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // মেইন ইমেজ
                AsyncImage(
                    model = e.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(220.dp).clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.height(20.dp))

                Text(text = e.title, color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(text = "Hosted by ${e.clubName}", color = PrimaryBlue, fontSize = 14.sp)

                Spacer(Modifier.height(16.dp))

                // ইনফো কার্ড
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardBackground)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Date: ${e.date}", color = TextWhite, fontWeight = FontWeight.Medium)
                        Text("Time: ${e.time}", color = TextWhite, fontWeight = FontWeight.Medium)
                        Text("Venue: ${e.location}", color = TextWhite, fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(Modifier.height(20.dp))

                Text("About Event", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(text = e.description, color = TextGray, fontSize = 15.sp, lineHeight = 22.sp)

                Spacer(Modifier.height(30.dp))

                // ইন্টারেস্ট বা জয়েন বাটন
                Button(
                    onClick = { /* জয়েন বাটন লজিক */ },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Remind Me / Join Event", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}