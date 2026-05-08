////package com.example.baustclubh.ui.screens.auth
////
////import android.widget.Toast
////import androidx.compose.foundation.background
////import androidx.compose.foundation.clickable
////import androidx.compose.foundation.layout.*
////import androidx.compose.foundation.shape.RoundedCornerShape
////import androidx.compose.material3.*
////import androidx.compose.runtime.*
////import androidx.compose.ui.Alignment
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.draw.clip
////import androidx.compose.ui.graphics.Color
////import androidx.compose.ui.platform.LocalContext
////import androidx.compose.ui.text.font.FontWeight
////import androidx.compose.ui.text.input.PasswordVisualTransformation
////import androidx.compose.ui.text.style.TextAlign
////import androidx.compose.ui.unit.dp
////import androidx.compose.ui.unit.sp
////import androidx.lifecycle.viewmodel.compose.viewModel
////import androidx.navigation.NavController
////import com.example.baustclubh.ui.theme.*
////import com.example.baustclubh.viewmodel.AuthViewModel
////import com.example.baustclubh.viewmodel.LoginState
////
////@Composable
////fun LoginScreen(
////    navController: NavController,
////    authViewModel: AuthViewModel = viewModel()
////) {
////    var studentId by remember { mutableStateOf("") }
////    var password by remember { mutableStateOf("") }
////    val loginState by authViewModel.loginState.collectAsState()
////    val context = LocalContext.current
////
////    LaunchedEffect(loginState) {
////        when (loginState) {
////            is LoginState.Success -> {
////                val user = (loginState as LoginState.Success).user
////                Toast.makeText(context, "Welcome ${user.name}!", Toast.LENGTH_SHORT).show()
////
////                if (user.role == "admin") {
////                    navController.navigate("admin_dashboard") {
////                        popUpTo("login") { inclusive = true }
////                    }
////                } else {
////                    navController.navigate("home") {
////                        popUpTo("login") { inclusive = true }
////                    }
////                }
////                authViewModel.resetStates()
////            }
////            is LoginState.Error -> {
////                Toast.makeText(context, (loginState as LoginState.Error).message, Toast.LENGTH_LONG).show()
////                authViewModel.resetStates()
////            }
////            else -> {}
////        }
////    }
////
////    Column(
////        modifier = Modifier
////            .fillMaxSize()
////            .background(BackgroundDark)
////            .padding(24.dp),
////        horizontalAlignment = Alignment.CenterHorizontally,
////        verticalArrangement = Arrangement.Center
////    ) {
////        // Logo
////        Box(
////            modifier = Modifier
////                .size(70.dp)
////                .clip(RoundedCornerShape(18.dp))
////                .background(PrimaryBlue),
////            contentAlignment = Alignment.Center
////        ) {
////            Text(
////                text = "BC",
////                color = TextWhite,
////                fontSize = 28.sp,
////                fontWeight = FontWeight.Bold
////            )
////        }
////
////        Spacer(modifier = Modifier.height(24.dp))
////
////        // Title
////        Text(
////            text = "Welcome Back",
////            color = TextWhite,
////            fontSize = 24.sp,
////            fontWeight = FontWeight.Bold
////        )
////
////        Text(
////            text = "Sign in to your BAUST account",
////            color = TextGray,
////            fontSize = 14.sp
////        )
////
////        Spacer(modifier = Modifier.height(32.dp))
////
////        // Student ID Field
////        OutlinedTextField(
////            value = studentId,
////            onValueChange = { studentId = it },
////            label = { Text("Student ID", color = TextGray) },
////            placeholder = { Text("e.g., 202410001", color = TextGray.copy(alpha = 0.5f)) },
////            modifier = Modifier.fillMaxWidth(),
////            colors = OutlinedTextFieldDefaults.colors(
////                unfocusedBorderColor = CardBackground,
////                focusedBorderColor = PrimaryBlue,
////                focusedLabelColor = PrimaryBlue,
////                cursorColor = PrimaryBlue
////            ),
////            shape = RoundedCornerShape(12.dp),
////            enabled = loginState !is LoginState.Loading
////        )
////
////        Spacer(modifier = Modifier.height(16.dp))
////
////        // Password Field
////        OutlinedTextField(
////            value = password,
////            onValueChange = { password = it },
////            label = { Text("Password", color = TextGray) },
////            modifier = Modifier.fillMaxWidth(),
////            visualTransformation = PasswordVisualTransformation(),
////            colors = OutlinedTextFieldDefaults.colors(
////                unfocusedBorderColor = CardBackground,
////                focusedBorderColor = PrimaryBlue,
////                focusedLabelColor = PrimaryBlue,
////                cursorColor = PrimaryBlue
////            ),
////            shape = RoundedCornerShape(12.dp),
////            enabled = loginState !is LoginState.Loading
////        )
////
////        Spacer(modifier = Modifier.height(8.dp))
////
////        // Forgot Password
////        Text(
////            text = "Forgot Password?",
////            color = PrimaryBlue,
////            fontSize = 12.sp,
////            modifier = Modifier
////                .align(Alignment.End)
////                .clickable {
////                    Toast.makeText(context, "Contact admin to reset password", Toast.LENGTH_SHORT).show()
////                }
////        )
////
////        Spacer(modifier = Modifier.height(24.dp))
////
////        // Sign In Button
////        Button(
////            onClick = {
////                if (studentId.isNotEmpty() && password.isNotEmpty()) {
////                    authViewModel.login(studentId, password)
////                } else {
////                    Toast.makeText(context, "Please enter Student ID and Password", Toast.LENGTH_SHORT).show()
////                }
////            },
////            modifier = Modifier
////                .fillMaxWidth()
////                .height(52.dp),
////            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
////            shape = RoundedCornerShape(12.dp),
////            enabled = loginState !is LoginState.Loading
////        ) {
////            if (loginState is LoginState.Loading) {
////                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = TextWhite)
////            } else {
////                Text("Sign In", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 16.sp)
////            }
////        }
////
////        Spacer(modifier = Modifier.height(16.dp))
////
////        // OR Divider
////        Row(
////            modifier = Modifier.fillMaxWidth(),
////            verticalAlignment = Alignment.CenterVertically
////        ) {
////            Divider(
////                modifier = Modifier.weight(1f),
////                color = CardBackground,
////                thickness = 1.dp
////            )
////            Text(
////                text = "  or  ",
////                color = TextGray,
////                fontSize = 12.sp
////            )
////            Divider(
////                modifier = Modifier.weight(1f),
////                color = CardBackground,
////                thickness = 1.dp
////            )
////        }
////
////        Spacer(modifier = Modifier.height(16.dp))
////
////        // Register as New Student Button
////        OutlinedButton(
////            onClick = { navController.navigate("register") },
////            modifier = Modifier
////                .fillMaxWidth()
////                .height(52.dp),
////            shape = RoundedCornerShape(12.dp)
////        ) {
////            Text(
////                text = "Register as New Student",
////                fontWeight = FontWeight.Medium,
////                color = PrimaryBlue,
////                fontSize = 15.sp
////            )
////        }
////
////        Spacer(modifier = Modifier.height(12.dp))
////
////        // Admin/Moderator Login Button
////        TextButton(
////            onClick = {
////                studentId = "admin"
////                password = "admin123"
////                authViewModel.login("admin", "admin123")
////            },
////            modifier = Modifier.fillMaxWidth()
////        ) {
////            Text(
////                text = "Admin / Moderator Login",
////                color = TextGray,
////                fontSize = 13.sp,
////                textAlign = TextAlign.Center
////            )
////        }
////
////        // ❌ Demo Credentials Card REMOVED
////    }
////}
//
//package com.example.baustclubh.ui.screens.auth
//
//import android.widget.Toast
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import com.example.baustclubh.ui.theme.*
//import com.example.baustclubh.viewmodel.AuthViewModel
//import com.example.baustclubh.viewmodel.LoginState
//
//@Composable
//fun LoginScreen(
//    navController: NavController,
//    authViewModel: AuthViewModel = viewModel()
//) {
//    var studentId by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    val loginState by authViewModel.loginState.collectAsState()
//    val context = LocalContext.current
//
//    LaunchedEffect(loginState) {
//        when (loginState) {
//            is LoginState.Success -> {
//                val user = (loginState as LoginState.Success).user
//                Toast.makeText(context, "Welcome ${user.name}!", Toast.LENGTH_SHORT).show()
//
//                if (user.role == "admin") {
//                    navController.navigate("admin_dashboard") {
//                        popUpTo("login") { inclusive = true }
//                    }
//                } else {
//                    navController.navigate("home") {
//                        popUpTo("login") { inclusive = true }
//                    }
//                }
//            }
//            is LoginState.Error -> {
//                Toast.makeText(context, (loginState as LoginState.Error).message, Toast.LENGTH_LONG).show()
//            }
//            else -> {}
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(BackgroundDark)
//            .padding(24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Box(
//            modifier = Modifier
//                .size(70.dp)
//                .clip(RoundedCornerShape(18.dp))
//                .background(PrimaryBlue),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(text = "BC", color = TextWhite, fontSize = 28.sp, fontWeight = FontWeight.Bold)
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Text(text = "Welcome Back", color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
//        Text(text = "Sign in to your BAUST account", color = TextGray, fontSize = 14.sp)
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        OutlinedTextField(
//            value = studentId,
//            onValueChange = { studentId = it },
//            label = { Text("Student ID", color = TextGray) },
//            placeholder = { Text("e.g., 202410001", color = TextGray.copy(alpha = 0.5f)) },
//            modifier = Modifier.fillMaxWidth(),
//            colors = OutlinedTextFieldDefaults.colors(
//                unfocusedBorderColor = CardBackground,
//                focusedBorderColor = PrimaryBlue
//            ),
//            shape = RoundedCornerShape(12.dp)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = password,
//            onValueChange = { password = it },
//            label = { Text("Password", color = TextGray) },
//            modifier = Modifier.fillMaxWidth(),
//            visualTransformation = PasswordVisualTransformation(),
//            colors = OutlinedTextFieldDefaults.colors(
//                unfocusedBorderColor = CardBackground,
//                focusedBorderColor = PrimaryBlue
//            ),
//            shape = RoundedCornerShape(12.dp)
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Text(
//            text = "Forgot Password?",
//            color = PrimaryBlue,
//            fontSize = 12.sp,
//            modifier = Modifier
//                .align(Alignment.End)
//                .clickable {
//                    Toast.makeText(context, "Contact admin to reset password", Toast.LENGTH_SHORT).show()
//                }
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Button(
//            onClick = {
//                if (studentId.isNotEmpty() && password.isNotEmpty()) {
//                    authViewModel.login(studentId, password)
//                } else {
//                    Toast.makeText(context, "Please enter Student ID and Password", Toast.LENGTH_SHORT).show()
//                }
//            },
//            modifier = Modifier.fillMaxWidth().height(52.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
//            shape = RoundedCornerShape(12.dp)
//        ) {
//            if (loginState is LoginState.Loading) {
//                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = TextWhite)
//            } else {
//                Text("Sign In", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 16.sp)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Divider(modifier = Modifier.weight(1f), color = CardBackground, thickness = 1.dp)
//            Text(text = "  or  ", color = TextGray, fontSize = 12.sp)
//            Divider(modifier = Modifier.weight(1f), color = CardBackground, thickness = 1.dp)
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedButton(
//            onClick = { navController.navigate("register") },
//            modifier = Modifier.fillMaxWidth().height(52.dp),
//            shape = RoundedCornerShape(12.dp)
//        ) {
//            Text("Register as New Student", fontWeight = FontWeight.Medium, color = PrimaryBlue, fontSize = 15.sp)
//        }
//    }
//}

//
//package com.example.baustclubh.ui.screens.auth
//
//import android.widget.Toast
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.baustclubh.ui.theme.*
//import com.example.baustclubh.viewmodel.AuthViewModel
//import com.example.baustclubh.viewmodel.LoginState
//
//@Composable
//fun LoginScreen(
//    navController: NavController,
//    authViewModel: AuthViewModel
//) {
//    var studentId by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    val loginState by authViewModel.loginState.collectAsState()
//    val context = LocalContext.current
//
//    // Navigation logic handle kora
//    LaunchedEffect(loginState) {
//        when (loginState) {
//            is LoginState.Success -> {
//                val user = (loginState as LoginState.Success).user
//                Toast.makeText(context, "Welcome ${user.name}!", Toast.LENGTH_SHORT).show()
//
//                // গুরুত্বপূর্ণ: আগে স্টেট রিসেট করো, তারপর নেভিগেট করো।
//                // এতে করে ব্যাকস্ট্যাক থেকে এন্ট্রি চলে গেলেও লুপ হবে না।
//                authViewModel.resetStates()
//
//                if (user.role == "admin") {
//                    navController.navigate("admin_dashboard") {
//                        popUpTo("login") { inclusive = true }
//                    }
//                } else {
//                    navController.navigate("home") {
//                        popUpTo("login") { inclusive = true }
//                    }
//                }
//            }
//            is LoginState.Error -> {
//                Toast.makeText(context, (loginState as LoginState.Error).message, Toast.LENGTH_LONG).show()
//                authViewModel.resetStates()
//            }
//            else -> {}
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(BackgroundDark)
//            .padding(24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        // Logo
//        Box(
//            modifier = Modifier
//                .size(70.dp)
//                .clip(RoundedCornerShape(18.dp))
//                .background(PrimaryBlue),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(text = "BC", color = TextWhite, fontSize = 28.sp, fontWeight = FontWeight.Bold)
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Title
//        Text(text = "Welcome Back", color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
//        Text(text = "Sign in to your BAUST account", color = TextGray, fontSize = 14.sp)
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Student ID Field
//        OutlinedTextField(
//            value = studentId,
//            onValueChange = { studentId = it },
//            label = { Text("Student ID", color = TextGray) },
//            placeholder = { Text("e.g., 202410001", color = TextGray.copy(alpha = 0.5f)) },
//            modifier = Modifier.fillMaxWidth(),
//            colors = OutlinedTextFieldDefaults.colors(
//                unfocusedBorderColor = CardBackground,
//                focusedBorderColor = PrimaryBlue,
//                focusedLabelColor = PrimaryBlue,
//                cursorColor = PrimaryBlue
//            ),
//            shape = RoundedCornerShape(12.dp),
//            enabled = loginState !is LoginState.Loading
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Password Field
//        OutlinedTextField(
//            value = password,
//            onValueChange = { password = it },
//            label = { Text("Password", color = TextGray) },
//            modifier = Modifier.fillMaxWidth(),
//            visualTransformation = PasswordVisualTransformation(),
//            colors = OutlinedTextFieldDefaults.colors(
//                unfocusedBorderColor = CardBackground,
//                focusedBorderColor = PrimaryBlue,
//                focusedLabelColor = PrimaryBlue,
//                cursorColor = PrimaryBlue
//            ),
//            shape = RoundedCornerShape(12.dp),
//            enabled = loginState !is LoginState.Loading
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Forgot Password
//        Text(
//            text = "Forgot Password?",
//            color = PrimaryBlue,
//            fontSize = 12.sp,
//            modifier = Modifier
//                .align(Alignment.End)
//                .clickable {
//                    Toast.makeText(context, "Contact admin to reset password", Toast.LENGTH_SHORT).show()
//                }
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Sign In Button
//        Button(
//            onClick = {
//                if (studentId.isNotEmpty() && password.isNotEmpty()) {
//                    authViewModel.login(studentId, password)
//                } else {
//                    Toast.makeText(context, "Please enter Student ID and Password", Toast.LENGTH_SHORT).show()
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(52.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
//            shape = RoundedCornerShape(12.dp),
//            enabled = loginState !is LoginState.Loading
//        ) {
//            if (loginState is LoginState.Loading) {
//                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = TextWhite)
//            } else {
//                Text("Sign In", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 16.sp)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Divider
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Divider(modifier = Modifier.weight(1f), color = CardBackground, thickness = 1.dp)
//            Text(text = "  or  ", color = TextGray, fontSize = 12.sp)
//            Divider(modifier = Modifier.weight(1f), color = CardBackground, thickness = 1.dp)
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Register Button
//        OutlinedButton(
//            onClick = { navController.navigate("register") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(52.dp),
//            shape = RoundedCornerShape(12.dp)
//        ) {
//            Text(
//                text = "Register as New Student",
//                fontWeight = FontWeight.Medium,
//                color = PrimaryBlue,
//                fontSize = 15.sp
//            )
//        }
//    }
//}

package com.example.baustclubh.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baustclubh.ui.theme.*
import com.example.baustclubh.viewmodel.AuthViewModel
import com.example.baustclubh.viewmodel.LoginState

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    // ট্যাব সিলেক্ট করার জন্য স্টেট (০ = User, ১ = Admin)
    var selectedTab by remember { mutableIntStateOf(0) }

    var studentId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginState by authViewModel.loginState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            val user = (loginState as LoginState.Success).user
            Toast.makeText(context, "Welcome ${user.name}!", Toast.LENGTH_SHORT).show()
            authViewModel.resetStates()

            if (user.role == "super_admin" || user.role == "dept_admin" || user.role == "club_admin") {
                navController.navigate("admin_dashboard") {
                    popUpTo("login") { inclusive = true }
                }
            } else {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
        } else if (loginState is LoginState.Error) {
            Toast.makeText(context, (loginState as LoginState.Error).message, Toast.LENGTH_LONG).show()
            authViewModel.resetStates()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(PrimaryBlue),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "BC", color = TextWhite, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- User & Admin Tab Switcher ---
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(45.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(CardBackground),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User Tab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(25.dp))
                    .background(if (selectedTab == 0) PrimaryBlue else Color.Transparent)
                    .clickable { selectedTab = 0 },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "User",
                    color = if (selectedTab == 0) TextWhite else TextGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
            // Admin Tab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(25.dp))
                    .background(if (selectedTab == 1) PrimaryBlue else Color.Transparent)
                    .clickable { selectedTab = 1 },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Admin",
                    color = if (selectedTab == 1) TextWhite else TextGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Title and Subtitle based on tab
        Text(
            text = if (selectedTab == 0) "Welcome Back" else "Admin Portal",
            color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold
        )
        Text(
            text = if (selectedTab == 0) "Sign in to your student account" else "Access club control panel",
            color = TextGray, fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Student ID / Admin ID Field
        OutlinedTextField(
            value = studentId,
            onValueChange = { studentId = it },
            label = { Text(if (selectedTab == 0) "Student ID" else "Admin ID", color = TextGray) },
            placeholder = { Text(if (selectedTab == 0) "e.g., 08024102..." else "Admin Username", color = TextGray.copy(alpha = 0.5f)) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                unfocusedBorderColor = CardBackground,
                focusedBorderColor = PrimaryBlue,
                cursorColor = PrimaryBlue
            ),
            shape = RoundedCornerShape(12.dp),
            enabled = loginState !is LoginState.Loading
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = TextGray) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                unfocusedBorderColor = CardBackground,
                focusedBorderColor = PrimaryBlue,
                cursorColor = PrimaryBlue
            ),
            shape = RoundedCornerShape(12.dp),
            enabled = loginState !is LoginState.Loading
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Sign In Button
        Button(
            onClick = {
                if (studentId.isNotEmpty() && password.isNotEmpty()) {
                    authViewModel.login(studentId, password)
                } else {
                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            shape = RoundedCornerShape(12.dp),
            enabled = loginState !is LoginState.Loading
        ) {
            if (loginState is LoginState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = TextWhite)
            } else {
                Text(
                    text = if (selectedTab == 0) "Sign In" else "Admin Login",
                    fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 16.sp
                )
            }
        }

        // রেজিস্ট্রেশন সেকশন শুধুমাত্র ইউজার ট্যাবে দেখাবে
        if (selectedTab == 0) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = CardBackground, thickness = 1.dp)
                Text(text = "  or  ", color = TextGray, fontSize = 12.sp)
                HorizontalDivider(modifier = Modifier.weight(1f), color = CardBackground, thickness = 1.dp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = { navController.navigate("register") },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBackground)
            ) {
                Text("Register as New Student", color = PrimaryBlue)
            }
        }
    }
}