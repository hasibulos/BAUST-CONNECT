package com.example.baustclubh.ui.screens.alumni

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.baustclubh.ui.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Alumni Network",
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Text(
                            text = "←",
                            color = TextWhite,
                            fontSize = 24.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundDark
                )
            )
        },
        bottomBar = { BottomNavBar(navController) },
        containerColor = BackgroundDark
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🎓",
                        fontSize = 48.sp
                    )
                    Text(
                        text = "BAUST Alumni Association",
                        color = TextWhite,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Connect with graduates from BAUST",
                        color = TextGray,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Alumni List Title
            Text(
                text = "Notable Alumni",
                color = TextWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Alumni List
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item { AlumniCard("Md. Rakibul Islam", "CSE, 2018", "Software Engineer @ Google", "🌍") }
                item { AlumniCard("Sadika Sultana", "EEE, 2019", "Research Assistant @ MIT", "🔬") }
                item { AlumniCard("Tanvir Ahmed", "BBA, 2017", "Entrepreneur", "💼") }
                item { AlumniCard("Fatema Tuz Zohra", "CSE, 2020", "Data Scientist @ Microsoft", "📊") }
                item { AlumniCard("Shahidul Islam", "ME, 2016", "Project Manager @ Tesla", "🚗") }
            }
        }
    }
}

@Composable
fun AlumniCard(name: String, batch: String, position: String, icon: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(PrimaryBlue, RoundedCornerShape(25.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    fontSize = 24.sp
                )
            }

            // Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    color = TextWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = batch,
                    color = PrimaryBlue,
                    fontSize = 12.sp
                )
                Text(
                    text = position,
                    color = TextGray,
                    fontSize = 12.sp
                )
            }

            // Connect Button
            TextButton(onClick = { /* Connect with alumni */ }) {
                Text(
                    text = "Connect",
                    color = PrimaryBlue,
                    fontSize = 12.sp
                )
            }
        }
    }
}