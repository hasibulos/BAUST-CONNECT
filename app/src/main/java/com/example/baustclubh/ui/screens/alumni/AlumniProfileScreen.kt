package com.example.baustclubh.ui.screens.alumni

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage // 👈 ইমেজ ইউআরএল থেকে ছবি লোড করার জন্য ইম্পোর্ট
import com.example.baustclubh.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniProfileScreen(
    navController: NavController,
    name: String,
    batch: String,
    position: String,
    icon: String, // 👈 লিস্ট থেকে পাঠানো ইমেজ ইউআরএল প্যারামিটার
    email: String,
    linkedin: String,
    facebook: String,
    bio: String
) {
    val context = LocalContext.current

    // সোশ্যাল লিঙ্ক ওপেন করার হেল্পার মেথড (আপনার অরিজিনাল লজিক)
    val openUrl = { url: String ->
        if (url.isNotBlank() && url != "null") {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(if (!url.startsWith("http")) "https://$url" else url))
                context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "Invalid Link", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Link not provided by user", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile Details", color = TextWhite, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Text("←", color = TextWhite, fontSize = 24.sp)
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🔷 ১. অ্যাভাটার / মেম্বার ছবি ব্যাকগ্রাউন্ড গ্রিড
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                // 🖼️ যদি ডাটাবেজে ইমেজ URL (http) থাকে তবে Coil দিয়ে আসল ছবি দেখাবে
                if (icon.isNotBlank() && icon.startsWith("http")) {
                    AsyncImage(
                        model = icon,
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(105.dp)
                            .clip(CircleShape)
                    )
                } else {
                    // 🎓 যদি ছবি না থাকে তবে আপনার আগের সেই চমৎকার রাউন্ডেড নামের প্রথম অক্ষরের ডিজাইনটি দেখাবে
                    Box(
                        modifier = Modifier
                            .size(95.dp)
                            .clip(CircleShape)
                            .background(PrimaryBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = name.take(1).uppercase(), color = TextWhite, fontSize = 38.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🔷 ২. নাম ও টাইটেল ইনফো
            Text(text = name, color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text = batch, color = PrimaryBlue, fontSize = 14.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 2.dp))

            Card(
                modifier = Modifier.padding(top = 10.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = java.net.URLDecoder.decode(position, "UTF-8"), // এনকোড করা পজিশন ডিকোড করে সুন্দরভাবে দেখাবে
                    color = TextWhite.copy(alpha = 0.9f),
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🔷 ৩. বায়োগ্রাফি / ডেসক্রিপশন বক্স
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "About", color = TextGray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))

                    // বায়ো ডিকোড করে সুন্দর টেক্সট দেখাবে
                    val decodedBio = try { java.net.URLDecoder.decode(bio, "UTF-8") } catch (e: Exception) { bio }
                    Text(
                        text = if (decodedBio.isBlank() || decodedBio == "null") "No bio added yet." else decodedBio,
                        color = TextWhite,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🔷 ৪. সোশ্যাল কানেক্ট বাটনস (আপনার অরিজিনাল ডিজাইন ও কালার স্কিম)
            Text(
                text = "Connect on Social Ecosystem",
                color = TextGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                // লিঙ্কডইন বাটন
                SocialButton(text = "Connect on LinkedIn", containerColor = Color(0xFF0077B5)) {
                    openUrl(linkedin)
                }

                // ফেসবুক বাটন
                SocialButton(text = "Follow on Facebook", containerColor = Color(0xFF1877F2)) {
                    openUrl(facebook)
                }

                // ইমেইল বাটন
                SocialButton(text = "Send Professional Email", containerColor = Color(0xFFEA4335)) {
                    if (email.isNotBlank() && email != "null") {
                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Email not provided", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun SocialButton(text: String, containerColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = text, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}