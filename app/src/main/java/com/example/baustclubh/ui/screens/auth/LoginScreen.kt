//////package com.example.baustclubh.ui.screens.auth
//////
//////import android.widget.Toast
//////import androidx.compose.foundation.background
//////import androidx.compose.foundation.clickable
//////import androidx.compose.foundation.layout.*
//////import androidx.compose.foundation.shape.RoundedCornerShape
//////import androidx.compose.material3.*
//////import androidx.compose.runtime.*
//////import androidx.compose.ui.Alignment
//////import androidx.compose.ui.Modifier
//////import androidx.compose.ui.draw.clip
//////import androidx.compose.ui.graphics.Color
//////import androidx.compose.ui.platform.LocalContext
//////import androidx.compose.ui.text.font.FontWeight
//////import androidx.compose.ui.text.input.PasswordVisualTransformation
//////import androidx.compose.ui.text.style.TextAlign
//////import androidx.compose.ui.unit.dp
//////import androidx.compose.ui.unit.sp
//////import androidx.lifecycle.viewmodel.compose.viewModel
//////import androidx.navigation.NavController
//////import com.example.baustclubh.ui.theme.*
//////import com.example.baustclubh.viewmodel.AuthViewModel
//////import com.example.baustclubh.viewmodel.LoginState
//////
//////@Composable
//////fun LoginScreen(
//////    navController: NavController,
//////    authViewModel: AuthViewModel = viewModel()
//////) {
//////    var studentId by remember { mutableStateOf("") }
//////    var password by remember { mutableStateOf("") }
//////    val loginState by authViewModel.loginState.collectAsState()
//////    val context = LocalContext.current
//////
//////    LaunchedEffect(loginState) {
//////        when (loginState) {
//////            is LoginState.Success -> {
//////                val user = (loginState as LoginState.Success).user
//////                Toast.makeText(context, "Welcome ${user.name}!", Toast.LENGTH_SHORT).show()
//////
//////                if (user.role == "admin") {
//////                    navController.navigate("admin_dashboard") {
//////                        popUpTo("login") { inclusive = true }
//////                    }
//////                } else {
//////                    navController.navigate("home") {
//////                        popUpTo("login") { inclusive = true }
//////                    }
//////                }
//////                authViewModel.resetStates()
//////            }
//////            is LoginState.Error -> {
//////                Toast.makeText(context, (loginState as LoginState.Error).message, Toast.LENGTH_LONG).show()
//////                authViewModel.resetStates()
//////            }
//////            else -> {}
//////        }
//////    }
//////
//////    Column(
//////        modifier = Modifier
//////            .fillMaxSize()
//////            .background(BackgroundDark)
//////            .padding(24.dp),
//////        horizontalAlignment = Alignment.CenterHorizontally,
//////        verticalArrangement = Arrangement.Center
//////    ) {
//////        // Logo
//////        Box(
//////            modifier = Modifier
//////                .size(70.dp)
//////                .clip(RoundedCornerShape(18.dp))
//////                .background(PrimaryBlue),
//////            contentAlignment = Alignment.Center
//////        ) {
//////            Text(
//////                text = "BC",
//////                color = TextWhite,
//////                fontSize = 28.sp,
//////                fontWeight = FontWeight.Bold
//////            )
//////        }
//////
//////        Spacer(modifier = Modifier.height(24.dp))
//////
//////        // Title
//////        Text(
//////            text = "Welcome Back",
//////            color = TextWhite,
//////            fontSize = 24.sp,
//////            fontWeight = FontWeight.Bold
//////        )
//////
//////        Text(
//////            text = "Sign in to your BAUST account",
//////            color = TextGray,
//////            fontSize = 14.sp
//////        )
//////
//////        Spacer(modifier = Modifier.height(32.dp))
//////
//////        // Student ID Field
//////        OutlinedTextField(
//////            value = studentId,
//////            onValueChange = { studentId = it },
//////            label = { Text("Student ID", color = TextGray) },
//////            placeholder = { Text("e.g., 202410001", color = TextGray.copy(alpha = 0.5f)) },
//////            modifier = Modifier.fillMaxWidth(),
//////            colors = OutlinedTextFieldDefaults.colors(
//////                unfocusedBorderColor = CardBackground,
//////                focusedBorderColor = PrimaryBlue,
//////                focusedLabelColor = PrimaryBlue,
//////                cursorColor = PrimaryBlue
//////            ),
//////            shape = RoundedCornerShape(12.dp),
//////            enabled = loginState !is LoginState.Loading
//////        )
//////
//////        Spacer(modifier = Modifier.height(16.dp))
//////
//////        // Password Field
//////        OutlinedTextField(
//////            value = password,
//////            onValueChange = { password = it },
//////            label = { Text("Password", color = TextGray) },
//////            modifier = Modifier.fillMaxWidth(),
//////            visualTransformation = PasswordVisualTransformation(),
//////            colors = OutlinedTextFieldDefaults.colors(
//////                unfocusedBorderColor = CardBackground,
//////                focusedBorderColor = PrimaryBlue,
//////                focusedLabelColor = PrimaryBlue,
//////                cursorColor = PrimaryBlue
//////            ),
//////            shape = RoundedCornerShape(12.dp),
//////            enabled = loginState !is LoginState.Loading
//////        )
//////
//////        Spacer(modifier = Modifier.height(8.dp))
//////
//////        // Forgot Password
//////        Text(
//////            text = "Forgot Password?",
//////            color = PrimaryBlue,
//////            fontSize = 12.sp,
//////            modifier = Modifier
//////                .align(Alignment.End)
//////                .clickable {
//////                    Toast.makeText(context, "Contact admin to reset password", Toast.LENGTH_SHORT).show()
//////                }
//////        )
//////
//////        Spacer(modifier = Modifier.height(24.dp))
//////
//////        // Sign In Button
//////        Button(
//////            onClick = {
//////                if (studentId.isNotEmpty() && password.isNotEmpty()) {
//////                    authViewModel.login(studentId, password)
//////                } else {
//////                    Toast.makeText(context, "Please enter Student ID and Password", Toast.LENGTH_SHORT).show()
//////                }
//////            },
//////            modifier = Modifier
//////                .fillMaxWidth()
//////                .height(52.dp),
//////            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
//////            shape = RoundedCornerShape(12.dp),
//////            enabled = loginState !is LoginState.Loading
//////        ) {
//////            if (loginState is LoginState.Loading) {
//////                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = TextWhite)
//////            } else {
//////                Text("Sign In", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 16.sp)
//////            }
//////        }
//////
//////        Spacer(modifier = Modifier.height(16.dp))
//////
//////        // OR Divider
//////        Row(
//////            modifier = Modifier.fillMaxWidth(),
//////            verticalAlignment = Alignment.CenterVertically
//////        ) {
//////            Divider(
//////                modifier = Modifier.weight(1f),
//////                color = CardBackground,
//////                thickness = 1.dp
//////            )
//////            Text(
//////                text = "  or  ",
//////                color = TextGray,
//////                fontSize = 12.sp
//////            )
//////            Divider(
//////                modifier = Modifier.weight(1f),
//////                color = CardBackground,
//////                thickness = 1.dp
//////            )
//////        }
//////
//////        Spacer(modifier = Modifier.height(16.dp))
//////
//////        // Register as New Student Button
//////        OutlinedButton(
//////            onClick = { navController.navigate("register") },
//////            modifier = Modifier
//////                .fillMaxWidth()
//////                .height(52.dp),
//////            shape = RoundedCornerShape(12.dp)
//////        ) {
//////            Text(
//////                text = "Register as New Student",
//////                fontWeight = FontWeight.Medium,
//////                color = PrimaryBlue,
//////                fontSize = 15.sp
//////            )
//////        }
//////
//////        Spacer(modifier = Modifier.height(12.dp))
//////
//////        // Admin/Moderator Login Button
//////        TextButton(
//////            onClick = {
//////                studentId = "admin"
//////                password = "admin123"
//////                authViewModel.login("admin", "admin123")
//////            },
//////            modifier = Modifier.fillMaxWidth()
//////        ) {
//////            Text(
//////                text = "Admin / Moderator Login",
//////                color = TextGray,
//////                fontSize = 13.sp,
//////                textAlign = TextAlign.Center
//////            )
//////        }
//////
//////        // ❌ Demo Credentials Card REMOVED
//////    }
//////}
////
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
////import androidx.compose.ui.platform.LocalContext
////import androidx.compose.ui.text.font.FontWeight
////import androidx.compose.ui.text.input.PasswordVisualTransformation
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
////            }
////            is LoginState.Error -> {
////                Toast.makeText(context, (loginState as LoginState.Error).message, Toast.LENGTH_LONG).show()
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
////        Box(
////            modifier = Modifier
////                .size(70.dp)
////                .clip(RoundedCornerShape(18.dp))
////                .background(PrimaryBlue),
////            contentAlignment = Alignment.Center
////        ) {
////            Text(text = "BC", color = TextWhite, fontSize = 28.sp, fontWeight = FontWeight.Bold)
////        }
////
////        Spacer(modifier = Modifier.height(24.dp))
////
////        Text(text = "Welcome Back", color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
////        Text(text = "Sign in to your BAUST account", color = TextGray, fontSize = 14.sp)
////
////        Spacer(modifier = Modifier.height(32.dp))
////
////        OutlinedTextField(
////            value = studentId,
////            onValueChange = { studentId = it },
////            label = { Text("Student ID", color = TextGray) },
////            placeholder = { Text("e.g., 202410001", color = TextGray.copy(alpha = 0.5f)) },
////            modifier = Modifier.fillMaxWidth(),
////            colors = OutlinedTextFieldDefaults.colors(
////                unfocusedBorderColor = CardBackground,
////                focusedBorderColor = PrimaryBlue
////            ),
////            shape = RoundedCornerShape(12.dp)
////        )
////
////        Spacer(modifier = Modifier.height(16.dp))
////
////        OutlinedTextField(
////            value = password,
////            onValueChange = { password = it },
////            label = { Text("Password", color = TextGray) },
////            modifier = Modifier.fillMaxWidth(),
////            visualTransformation = PasswordVisualTransformation(),
////            colors = OutlinedTextFieldDefaults.colors(
////                unfocusedBorderColor = CardBackground,
////                focusedBorderColor = PrimaryBlue
////            ),
////            shape = RoundedCornerShape(12.dp)
////        )
////
////        Spacer(modifier = Modifier.height(8.dp))
////
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
////        Button(
////            onClick = {
////                if (studentId.isNotEmpty() && password.isNotEmpty()) {
////                    authViewModel.login(studentId, password)
////                } else {
////                    Toast.makeText(context, "Please enter Student ID and Password", Toast.LENGTH_SHORT).show()
////                }
////            },
////            modifier = Modifier.fillMaxWidth().height(52.dp),
////            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
////            shape = RoundedCornerShape(12.dp)
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
////        Row(
////            modifier = Modifier.fillMaxWidth(),
////            verticalAlignment = Alignment.CenterVertically
////        ) {
////            Divider(modifier = Modifier.weight(1f), color = CardBackground, thickness = 1.dp)
////            Text(text = "  or  ", color = TextGray, fontSize = 12.sp)
////            Divider(modifier = Modifier.weight(1f), color = CardBackground, thickness = 1.dp)
////        }
////
////        Spacer(modifier = Modifier.height(16.dp))
////
////        OutlinedButton(
////            onClick = { navController.navigate("register") },
////            modifier = Modifier.fillMaxWidth().height(52.dp),
////            shape = RoundedCornerShape(12.dp)
////        ) {
////            Text("Register as New Student", fontWeight = FontWeight.Medium, color = PrimaryBlue, fontSize = 15.sp)
////        }
////    }
////}
//
////
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
////import androidx.compose.ui.platform.LocalContext
////import androidx.compose.ui.text.font.FontWeight
////import androidx.compose.ui.text.input.PasswordVisualTransformation
////import androidx.compose.ui.text.style.TextAlign
////import androidx.compose.ui.unit.dp
////import androidx.compose.ui.unit.sp
////import androidx.navigation.NavController
////import com.example.baustclubh.ui.theme.*
////import com.example.baustclubh.viewmodel.AuthViewModel
////import com.example.baustclubh.viewmodel.LoginState
////
////@Composable
////fun LoginScreen(
////    navController: NavController,
////    authViewModel: AuthViewModel
////) {
////    var studentId by remember { mutableStateOf("") }
////    var password by remember { mutableStateOf("") }
////    val loginState by authViewModel.loginState.collectAsState()
////    val context = LocalContext.current
////
////    // Navigation logic handle kora
////    LaunchedEffect(loginState) {
////        when (loginState) {
////            is LoginState.Success -> {
////                val user = (loginState as LoginState.Success).user
////                Toast.makeText(context, "Welcome ${user.name}!", Toast.LENGTH_SHORT).show()
////
////                // গুরুত্বপূর্ণ: আগে স্টেট রিসেট করো, তারপর নেভিগেট করো।
////                // এতে করে ব্যাকস্ট্যাক থেকে এন্ট্রি চলে গেলেও লুপ হবে না।
////                authViewModel.resetStates()
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
////            Text(text = "BC", color = TextWhite, fontSize = 28.sp, fontWeight = FontWeight.Bold)
////        }
////
////        Spacer(modifier = Modifier.height(24.dp))
////
////        // Title
////        Text(text = "Welcome Back", color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
////        Text(text = "Sign in to your BAUST account", color = TextGray, fontSize = 14.sp)
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
////        // Divider
////        Row(
////            modifier = Modifier.fillMaxWidth(),
////            verticalAlignment = Alignment.CenterVertically
////        ) {
////            Divider(modifier = Modifier.weight(1f), color = CardBackground, thickness = 1.dp)
////            Text(text = "  or  ", color = TextGray, fontSize = 12.sp)
////            Divider(modifier = Modifier.weight(1f), color = CardBackground, thickness = 1.dp)
////        }
////
////        Spacer(modifier = Modifier.height(16.dp))
////
////        // Register Button
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
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.PasswordVisualTransformation
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
//    // ট্যাব সিলেক্ট করার জন্য স্টেট (০ = User, ১ = Admin)
//    var selectedTab by remember { mutableIntStateOf(0) }
//
//    var studentId by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//
//    val loginState by authViewModel.loginState.collectAsState()
//    val context = LocalContext.current
//
//    LaunchedEffect(loginState) {
//        if (loginState is LoginState.Success) {
//            val user = (loginState as LoginState.Success).user
//            Toast.makeText(context, "Welcome ${user.name}!", Toast.LENGTH_SHORT).show()
//            authViewModel.resetStates()
//
//            if (user.role == "super_admin" || user.role == "dept_admin" || user.role == "club_admin") {
//                navController.navigate("admin_dashboard") {
//                    popUpTo("login") { inclusive = true }
//                }
//            } else {
//                navController.navigate("home") {
//                    popUpTo("login") { inclusive = true }
//                }
//            }
//        } else if (loginState is LoginState.Error) {
//            Toast.makeText(context, (loginState as LoginState.Error).message, Toast.LENGTH_LONG).show()
//            authViewModel.resetStates()
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
//        // --- User & Admin Tab Switcher ---
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(0.8f)
//                .height(45.dp)
//                .clip(RoundedCornerShape(25.dp))
//                .background(CardBackground),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // User Tab
//            Box(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxHeight()
//                    .clip(RoundedCornerShape(25.dp))
//                    .background(if (selectedTab == 0) PrimaryBlue else Color.Transparent)
//                    .clickable { selectedTab = 0 },
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "User",
//                    color = if (selectedTab == 0) TextWhite else TextGray,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 14.sp
//                )
//            }
//            // Admin Tab
//            Box(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxHeight()
//                    .clip(RoundedCornerShape(25.dp))
//                    .background(if (selectedTab == 1) PrimaryBlue else Color.Transparent)
//                    .clickable { selectedTab = 1 },
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "Admin",
//                    color = if (selectedTab == 1) TextWhite else TextGray,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 14.sp
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Title and Subtitle based on tab
//        Text(
//            text = if (selectedTab == 0) "Welcome Back" else "Admin Portal",
//            color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold
//        )
//        Text(
//            text = if (selectedTab == 0) "Sign in to your student account" else "Access club control panel",
//            color = TextGray, fontSize = 14.sp
//        )
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Student ID / Admin ID Field
//        OutlinedTextField(
//            value = studentId,
//            onValueChange = { studentId = it },
//            label = { Text(if (selectedTab == 0) "Student ID" else "Admin ID", color = TextGray) },
//            placeholder = { Text(if (selectedTab == 0) "e.g., 08024102..." else "Admin Username", color = TextGray.copy(alpha = 0.5f)) },
//            modifier = Modifier.fillMaxWidth(),
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedTextColor = TextWhite,
//                unfocusedTextColor = TextWhite,
//                unfocusedBorderColor = CardBackground,
//                focusedBorderColor = PrimaryBlue,
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
//                focusedTextColor = TextWhite,
//                unfocusedTextColor = TextWhite,
//                unfocusedBorderColor = CardBackground,
//                focusedBorderColor = PrimaryBlue,
//                cursorColor = PrimaryBlue
//            ),
//            shape = RoundedCornerShape(12.dp),
//            enabled = loginState !is LoginState.Loading
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
//                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
//                }
//            },
//            modifier = Modifier.fillMaxWidth().height(52.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
//            shape = RoundedCornerShape(12.dp),
//            enabled = loginState !is LoginState.Loading
//        ) {
//            if (loginState is LoginState.Loading) {
//                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = TextWhite)
//            } else {
//                Text(
//                    text = if (selectedTab == 0) "Sign In" else "Admin Login",
//                    fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 16.sp
//                )
//            }
//        }
//
//        // রেজিস্ট্রেশন সেকশন শুধুমাত্র ইউজার ট্যাবে দেখাবে
//        if (selectedTab == 0) {
//            Spacer(modifier = Modifier.height(16.dp))
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                HorizontalDivider(modifier = Modifier.weight(1f), color = CardBackground, thickness = 1.dp)
//                Text(text = "  or  ", color = TextGray, fontSize = 12.sp)
//                HorizontalDivider(modifier = Modifier.weight(1f), color = CardBackground, thickness = 1.dp)
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//            OutlinedButton(
//                onClick = { navController.navigate("register") },
//                modifier = Modifier.fillMaxWidth().height(52.dp),
//                shape = RoundedCornerShape(12.dp),
//                border = androidx.compose.foundation.BorderStroke(1.dp, CardBackground)
//            ) {
//                Text("Register as New Student", color = PrimaryBlue)
//            }
//        }
//    }
//}




//
//// Username/Email: superadmin@baust.com
//// Password:      12345678
//package com.example.baustclubh.ui.screens.auth
//
//import android.widget.Toast
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Visibility
//import androidx.compose.material.icons.filled.VisibilityOff
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.focus.FocusDirection
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalFocusManager
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
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
//    var selectedTab by remember { mutableIntStateOf(0) }
//    var studentId by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//
//    // 👁️ পাসওয়ার্ড ভিজিবিলিটি স্টেট
//    var passwordVisible by remember { mutableStateOf(false) }
//
//    // ⌨️ কীবোর্ড ফোকাস ম্যানেজার
//    val focusManager = LocalFocusManager.current
//
//    val loginState by authViewModel.loginState.collectAsState()
//    val currentUser by authViewModel.currentUser.collectAsState()
//    val context = LocalContext.current
//
//    // 🔄 ১. অটো-লগইন লজিক: ইউজার অলরেডি লগইন থাকলে সরাসরি স্ক্রিন রিডাইরেক্ট হবে
//    LaunchedEffect(currentUser) {
//        currentUser?.let { user ->
//            if (user.role == "super_admin" || user.role == "dept_admin" || user.role == "club_admin") {
//                navController.navigate("admin_dashboard") {
//                    popUpTo("login") { inclusive = true }
//                }
//            } else {
//                navController.navigate("home") {
//                    popUpTo("login") { inclusive = true }
//                }
//            }
//        }
//    }
//
//    // ফায়ারবেস লগইন স্টেট লিসেনার
//    LaunchedEffect(loginState) {
//        if (loginState is LoginState.Success) {
//            val user = (loginState as LoginState.Success).user
//            Toast.makeText(context, "Welcome ${user.name}!", Toast.LENGTH_SHORT).show()
//            authViewModel.resetStates()
//
//            if (user.role == "super_admin" || user.role == "dept_admin" || user.role == "club_admin") {
//                navController.navigate("admin_dashboard") {
//                    popUpTo("login") { inclusive = true }
//                }
//            } else {
//                navController.navigate("home") {
//                    popUpTo("login") { inclusive = true }
//                }
//            }
//        } else if (loginState is LoginState.Error) {
//            Toast.makeText(context, (loginState as LoginState.Error).message, Toast.LENGTH_LONG).show()
//            authViewModel.resetStates()
//        }
//    }
//
//    // 🛠️ লগইন এক্সিকিউট করার জন্য কমন লোকাল ফাংশন (যাতে বাটনে ও কীবোর্ড এন্টারে একই লজিক চলে)
//    val performLogin = {
//        if (studentId.isNotEmpty() && password.isNotEmpty()) {
//            if (selectedTab == 1 && studentId == "superadmin@baust.com" && password == "12345678") {
//                Toast.makeText(context, "Welcome Super Admin!", Toast.LENGTH_SHORT).show()
//                navController.currentBackStackEntry?.savedStateHandle?.set("forced_role", "super_admin")
//                navController.navigate("admin_dashboard") {
//                    popUpTo("login") { inclusive = true }
//                }
//            } else {
//                authViewModel.login(studentId, password)
//            }
//        } else {
//            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
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
//        // --- User & Admin Tab Switcher ---
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(0.8f)
//                .height(45.dp)
//                .clip(RoundedCornerShape(25.dp))
//                .background(CardBackground),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Box(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxHeight()
//                    .clip(RoundedCornerShape(25.dp))
//                    .background(if (selectedTab == 0) PrimaryBlue else Color.Transparent)
//                    .clickable { selectedTab = 0 },
//                contentAlignment = Alignment.Center
//            ) {
//                Text(text = "User", color = if (selectedTab == 0) TextWhite else TextGray, fontWeight = FontWeight.Bold, fontSize = 14.sp)
//            }
//            Box(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxHeight()
//                    .clip(RoundedCornerShape(25.dp))
//                    .background(if (selectedTab == 1) PrimaryBlue else Color.Transparent)
//                    .clickable { selectedTab = 1 },
//                contentAlignment = Alignment.Center
//            ) {
//                Text(text = "Admin", color = if (selectedTab == 1) TextWhite else TextGray, fontWeight = FontWeight.Bold, fontSize = 14.sp)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        Text(text = if (selectedTab == 0) "Welcome Back" else "Admin Portal", color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
//        Text(text = if (selectedTab == 0) "Sign in to your student account" else "Access club control panel", color = TextGray, fontSize = 14.sp)
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Student ID / Admin ID Field
//        OutlinedTextField(
//            value = studentId,
//            onValueChange = { studentId = it },
//            label = { Text(if (selectedTab == 0) "Student ID" else "Admin ID / Email", color = TextGray) },
//            placeholder = { Text(if (selectedTab == 0) "e.g., 08024102..." else "superadmin@baust.com", color = TextGray.copy(alpha = 0.5f)) },
//            modifier = Modifier.fillMaxWidth(),
//            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, unfocusedBorderColor = CardBackground, focusedBorderColor = PrimaryBlue, cursorColor = PrimaryBlue),
//            shape = RoundedCornerShape(12.dp),
//            enabled = loginState !is LoginState.Loading,
//            // ⌨️ এন্টার চাপলে নিচের ফিল্ডে যাওয়ার লজিক
//            keyboardOptions = KeyboardOptions(
//                keyboardType = if (selectedTab == 0) KeyboardType.Number else KeyboardType.Email,
//                imeAction = ImeAction.Next
//            ),
//            keyboardActions = KeyboardActions(
//                onNext = { focusManager.moveFocus(FocusDirection.Down) }
//            )
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
//            // 👁️ পাসওয়ার্ড দেখা বা না দেখার ট্রান্সফর্মেশন ট্রিক
//            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//            // 👁️ ডানপাশে চোখ আইকন যুক্ত করা
//            trailingIcon = {
//                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
//                val description = if (passwordVisible) "Hide password" else "Show password"
//
//                IconButton(onClick = { passwordVisible = !passwordVisible }) {
//                    Icon(imageVector = image, contentDescription = description, tint = TextGray)
//                }
//            },
//            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, unfocusedBorderColor = CardBackground, focusedBorderColor = PrimaryBlue, cursorColor = PrimaryBlue),
//            shape = RoundedCornerShape(12.dp),
//            enabled = loginState !is LoginState.Loading,
//            // ⌨️ পাসওয়ার্ডে এন্টার চাপলে সরাসরি লগইন রান করার লজিক
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Password,
//                imeAction = ImeAction.Done
//            ),
//            keyboardActions = KeyboardActions(
//                onDone = {
//                    focusManager.clearFocus()
//                    performLogin()
//                }
//            )
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Sign In Button
//        Button(
//            onClick = { performLogin() },
//            modifier = Modifier.fillMaxWidth().height(52.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
//            shape = RoundedCornerShape(12.dp),
//            enabled = loginState !is LoginState.Loading
//        ) {
//            if (loginState is LoginState.Loading) {
//                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = TextWhite)
//            } else {
//                Text(text = if (selectedTab == 0) "Sign In" else "Admin Login", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 16.sp)
//            }
//        }
//
//        if (selectedTab == 0) {
//            Spacer(modifier = Modifier.height(16.dp))
//            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
//                HorizontalDivider(modifier = Modifier.weight(1f), color = CardBackground, thickness = 1.dp)
//                Text(text = "  or  ", color = TextGray, fontSize = 12.sp)
//                HorizontalDivider(modifier = Modifier.weight(1f), color = CardBackground, thickness = 1.dp)
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//            OutlinedButton(
//                onClick = { navController.navigate("register") },
//                modifier = Modifier.fillMaxWidth().height(52.dp),
//                shape = RoundedCornerShape(12.dp),
//                border = androidx.compose.foundation.BorderStroke(1.dp, CardBackground)
//            ) {
//                Text("Register as New Student", color = PrimaryBlue)
//            }
//        }
//    }
//}

// Username/Email: superadmin@baust.com
// Password:      12345678
package com.example.baustclubh.ui.screens.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baustclubh.ui.theme.*
import com.example.baustclubh.viewmodel.AuthViewModel
import com.example.baustclubh.viewmodel.LoginState
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
// আপনার প্রজেক্টের আর (R) ফাইলটি ইমপোর্ট করতে হতে পারে, যেমন:
import com.example.baustclubh.R

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var studentId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // 👁️ পাসওয়ার্ড ভিজিবিলিটি স্টেট
    var passwordVisible by remember { mutableStateOf(false) }

    // ⌨️ কীবোর্ড ফোকাস ম্যানেজার
    val focusManager = LocalFocusManager.current

    val loginState by authViewModel.loginState.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()
    val context = LocalContext.current

    // 💾 পার্মানেন্ট সেশন স্টোরেজ (SharedPreferences)
    val sharedPreferences = remember { context.getSharedPreferences("baust_club_prefs", Context.MODE_PRIVATE) }

    // 🔄 ১. অবিনশ্বর অটো-লগইন লজিক: অ্যাপ সম্পূর্ণ কেটে ঢুকলেও এটি চেক করবে লোকাল মেমোরি
    LaunchedEffect(Unit) {
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
        val savedRole = sharedPreferences.getString("user_role", "")

        if (isLoggedIn && !savedRole.isNullOrEmpty()) {
            if (savedRole == "super_admin" || savedRole == "dept_admin" || savedRole == "club_admin") {
                navController.navigate("admin_dashboard") {
                    popUpTo("login") { inclusive = true }
                }
            } else {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }

    // ফায়ারবেস লগইন স্টেট লিসেনার (নরমাল ইউজার/অ্যাডমিনদের সেশন মেমোরিতে সেভ করার জন্য)
    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            val user = (loginState as LoginState.Success).user
            Toast.makeText(context, "Welcome ${user.name}!", Toast.LENGTH_SHORT).show()

            // 💾 সাকসেস হলে লোকাল মেমোরিতে স্টেট পার্মানেন্ট করা হচ্ছে
            sharedPreferences.edit().apply {
                putBoolean("is_logged_in", true)
                putString("user_role", user.role)
                apply()
            }

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

    // 🛠️ লগইন এক্সিকিউট করার জন্য কমন লোকাল ফাংশন
    val performLogin = {
        if (studentId.isNotEmpty() && password.isNotEmpty()) {
            if (selectedTab == 1 && studentId == "super" && password == "12345678") {
                Toast.makeText(context, "Welcome Super Admin!", Toast.LENGTH_SHORT).show()

                // 💾 সুপারঅ্যাডমিন বাইপাস সেশন লোকাল মেমোরিতে পার্মানেন্ট সেভ করা হলো
                sharedPreferences.edit().apply {
                    putBoolean("is_logged_in", true)
                    putString("user_role", "super_admin")
                    apply()
                }

                navController.currentBackStackEntry?.savedStateHandle?.set("forced_role", "super_admin")
                navController.navigate("admin_dashboard") {
                    popUpTo("login") { inclusive = true }
                }
            } else {
                authViewModel.login(studentId, password)
            }
        } else {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
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
        // --- পুরো স্ক্রিন জুড়ে টপ ব্যানার লোগো সেকশন ---
        Image(
            painter = painterResource(id = R.drawable.ic_baust_connect_logo),
            contentDescription = "BAUST CONNECT Banner Logo",
            modifier = Modifier
                .fillMaxWidth() // 📱 বাম থেকে ডান পুরো স্ক্রিন জুড়ে জায়গা নিবে
                .height(180.dp), // ব্যানারের জন্য সুন্দর একটি স্ট্যান্ডার্ড হাইট
            contentScale = ContentScale.Crop // 🎯 এটি পুরো জায়গা জুড়ে ইমেজটিকে ক্রপ করে পারফেক্টলি ফিট করবে, চ্যাপ্টা করবে না
        )

        Spacer(modifier = Modifier.height(24.dp)) // লোগো আর User/Admin ট্যাবের মাঝের গ্যাপ
// ---------------------------------------------
        // --- User & Admin Tab Switcher ---
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(45.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(CardBackground),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(25.dp))
                    .background(if (selectedTab == 0) PrimaryBlue else Color.Transparent)
                    .clickable { selectedTab = 0 },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "User", color = if (selectedTab == 0) TextWhite else TextGray, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(25.dp))
                    .background(if (selectedTab == 1) PrimaryBlue else Color.Transparent)
                    .clickable { selectedTab = 1 },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Admin", color = if (selectedTab == 1) TextWhite else TextGray, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = if (selectedTab == 0) "Welcome Back" else "Admin Portal", color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = if (selectedTab == 0) "Sign in to your student account" else "Access club control panel", color = TextGray, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(32.dp))

        // Student ID / Admin ID Field
        OutlinedTextField(
            value = studentId,
            onValueChange = { studentId = it },
            label = { Text(if (selectedTab == 0) "Student ID" else "Admin ID / Email", color = TextGray) },
            placeholder = { Text(if (selectedTab == 0) "e.g., 08024102..." else "superadmin@baust.com", color = TextGray.copy(alpha = 0.5f)) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, unfocusedBorderColor = CardBackground, focusedBorderColor = PrimaryBlue, cursorColor = PrimaryBlue),
            shape = RoundedCornerShape(12.dp),
            enabled = loginState !is LoginState.Loading,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (selectedTab == 0) KeyboardType.Number else KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = TextGray) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description, tint = TextGray)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, unfocusedBorderColor = CardBackground, focusedBorderColor = PrimaryBlue, cursorColor = PrimaryBlue),
            shape = RoundedCornerShape(12.dp),
            enabled = loginState !is LoginState.Loading,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    performLogin()
                }
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Sign In Button
        Button(
            onClick = { performLogin() },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            shape = RoundedCornerShape(12.dp),
            enabled = loginState !is LoginState.Loading
        ) {
            if (loginState is LoginState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = TextWhite)
            } else {
                Text(text = if (selectedTab == 0) "Sign In" else "Admin Login", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 16.sp)
            }
        }

        if (selectedTab == 0) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
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