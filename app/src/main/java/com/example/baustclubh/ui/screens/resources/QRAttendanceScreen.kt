package com.example.baustclubh.ui.screens.resources

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baustclubh.ui.theme.*
import java.util.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRAttendanceScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("QR Attendance", color = TextWhite, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundDark)
            )
        },
        containerColor = BackgroundDark
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ১. ইউজার কিউআর কোড সেকশন
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Your Unique QR Code", color = TextGray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(20.dp))

                    // --- Authentic QR Code Canvas Design ---
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val canvasSize = size.width
                            val gridCount = 12 // ১০x১০ এর গ্রিড
                            val cellSize = canvasSize / gridCount

                            // কাস্টম কিউআর প্যাটার্ন ড্র করা
                            // ১. পজিশন মার্কার (৩ কোণায় ৩টি বড় বক্স)
                            val markerSizeInCells = 3
                            val markerSize = cellSize * markerSizeInCells
                            val markers = listOf(
                                Offset(0f, 0f), // Top Left
                                Offset(canvasSize - markerSize, 0f), // Top Right
                                Offset(0f, canvasSize - markerSize) // Bottom Left
                            )

                            markers.forEach { pos ->
                                // Outer Square
                                drawRect(Color.Black, pos, Size(markerSize, markerSize))
                                // Inner White Square
                                drawRect(Color.White, pos + Offset(cellSize/2, cellSize/2), Size(markerSize - cellSize, markerSize - cellSize))
                                // Center Black Dot
                                drawRect(Color.Black, pos + Offset(cellSize, cellSize), Size(markerSize - cellSize * 2, markerSize - cellSize * 2))
                            }

                            // ২. র‍্যান্ডম ডট জেনারেট করা (Authentic লুকের জন্য)
                            val random = Random(12345) // ফিক্সড সিড যাতে কিউআর চেঞ্জ না হয়
                            for (i in 0 until gridCount) {
                                for (j in 0 until gridCount) {
                                    // মার্কার এরিয়া চেক (যাতে মার্কারের ওপর ডট না পড়ে)
                                    val isInsideMarker = (i < 4 && j < 4) || (i > 7 && j < 4) || (i < 4 && j > 7)

                                    if (!isInsideMarker) {
                                        if (random.nextBoolean()) {
                                            drawRect(
                                                color = Color.Black,
                                                topLeft = Offset(i * cellSize + cellSize/6, j * cellSize + cellSize/6),
                                                size = Size(cellSize/1.4f, cellSize/1.4f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // আইডি টেক্সট ডিজাইন
                    Surface(
                        color = PrimaryBlue.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "STU-0802-4102-1070",
                            color = PrimaryBlue,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            letterSpacing = 1.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Show this to coordinator to mark attendance", color = TextGray, fontSize = 11.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ২. রিসেন্ট অ্যাটেনডেন্স লিস্ট
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Text("Recent Attendance", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val mockEvents = listOf(
                    "Programming Contest" to "Mar 15, 2024",
                    "Robotics Workshop" to "Mar 10, 2024",
                    "Debate Practice" to "Mar 05, 2024"
                )
                items(mockEvents) { event ->
                    AttendanceItem(event.first, event.second)
                }
            }
        }
    }
}

@Composable
fun AttendanceItem(title: String, date: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, color = TextWhite, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(date, color = TextGray, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            // স্ট্যাটাস ডট
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color(0xFF4CAF50), RoundedCornerShape(4.dp))
            )
        }
    }
}