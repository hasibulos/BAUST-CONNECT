//package com.example.baustclubh.ui.screens.auth
//
//import android.widget.Toast
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import com.example.baustclubh.data.model.User
//import com.example.baustclubh.ui.theme.*
//import com.example.baustclubh.viewmodel.AuthViewModel
//import com.example.baustclubh.viewmodel.RegisterState
//
//@Composable
//fun RegisterScreen(
//    navController: NavController,
//    authViewModel: AuthViewModel = viewModel()
//) {
//    var name by remember { mutableStateOf("") }
//    var studentId by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var confirmPassword by remember { mutableStateOf("") }
//    var department by remember { mutableStateOf("") }
//    var batch by remember { mutableStateOf("") }
//
//    val registerState by authViewModel.registerState.collectAsState()
//    val context = LocalContext.current
//
//    LaunchedEffect(registerState) {
//        when (registerState) {
//            is RegisterState.Success -> {
//                Toast.makeText(context, "Registration Successful! Please Login.", Toast.LENGTH_LONG).show()
//                navController.navigate("login") {
//                    popUpTo("register") { inclusive = true }
//                }
//                authViewModel.resetStates()
//            }
//            is RegisterState.Error -> {
//                Toast.makeText(context, (registerState as RegisterState.Error).message, Toast.LENGTH_LONG).show()
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
//        Text("Create Account", color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
//        Text("Join BAUST Club Hub", color = TextGray, fontSize = 14.sp)
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        OutlinedTextField(
//            value = name,
//            onValueChange = { name = it },
//            label = { Text("Full Name", color = TextGray) },
//            modifier = Modifier.fillMaxWidth(),
//            colors = OutlinedTextFieldDefaults.colors(
//                unfocusedBorderColor = CardBackground,
//                focusedBorderColor = PrimaryBlue
//            ),
//            enabled = registerState !is RegisterState.Loading
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        OutlinedTextField(
//            value = studentId,
//            onValueChange = { studentId = it },
//            label = { Text("Student ID", color = TextGray) },
//            modifier = Modifier.fillMaxWidth(),
//            colors = OutlinedTextFieldDefaults.colors(
//                unfocusedBorderColor = CardBackground,
//                focusedBorderColor = PrimaryBlue
//            ),
//            enabled = registerState !is RegisterState.Loading
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text("Email", color = TextGray) },
//            modifier = Modifier.fillMaxWidth(),
//            colors = OutlinedTextFieldDefaults.colors(
//                unfocusedBorderColor = CardBackground,
//                focusedBorderColor = PrimaryBlue
//            ),
//            enabled = registerState !is RegisterState.Loading
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        OutlinedTextField(
//            value = department,
//            onValueChange = { department = it },
//            label = { Text("Department", color = TextGray) },
//            modifier = Modifier.fillMaxWidth(),
//            colors = OutlinedTextFieldDefaults.colors(
//                unfocusedBorderColor = CardBackground,
//                focusedBorderColor = PrimaryBlue
//            ),
//            enabled = registerState !is RegisterState.Loading
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        OutlinedTextField(
//            value = batch,
//            onValueChange = { batch = it },
//            label = { Text("Batch", color = TextGray) },
//            modifier = Modifier.fillMaxWidth(),
//            colors = OutlinedTextFieldDefaults.colors(
//                unfocusedBorderColor = CardBackground,
//                focusedBorderColor = PrimaryBlue
//            ),
//            enabled = registerState !is RegisterState.Loading
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        OutlinedTextField(
//            value = password,
//            onValueChange = { password = it },
//            label = { Text("Password", color = TextGray) },
//            modifier = Modifier.fillMaxWidth(),
//            colors = OutlinedTextFieldDefaults.colors(
//                unfocusedBorderColor = CardBackground,
//                focusedBorderColor = PrimaryBlue
//            ),
//            enabled = registerState !is RegisterState.Loading
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        OutlinedTextField(
//            value = confirmPassword,
//            onValueChange = { confirmPassword = it },
//            label = { Text("Confirm Password", color = TextGray) },
//            modifier = Modifier.fillMaxWidth(),
//            colors = OutlinedTextFieldDefaults.colors(
//                unfocusedBorderColor = CardBackground,
//                focusedBorderColor = PrimaryBlue
//            ),
//            enabled = registerState !is RegisterState.Loading
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Button(
//            onClick = {
//                when {
//                    name.isEmpty() -> Toast.makeText(context, "Enter full name", Toast.LENGTH_SHORT).show()
//                    studentId.isEmpty() -> Toast.makeText(context, "Enter student ID", Toast.LENGTH_SHORT).show()
//                    email.isEmpty() -> Toast.makeText(context, "Enter email", Toast.LENGTH_SHORT).show()
//                    password.isEmpty() -> Toast.makeText(context, "Enter password", Toast.LENGTH_SHORT).show()
//                    password != confirmPassword -> Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT).show()
//                    else -> {
//                        val user = User(
//                            studentId = studentId,
//                            name = name,
//                            email = email,
//                            department = department,
//                            batch = batch,
//                            password = password,
//                            role = "student",
//                            registeredClubs = emptyList(),
//                            joinDate = getCurrentDate()
//                        )
//                        authViewModel.register(user)
//                    }
//                }
//            },
//            modifier = Modifier.fillMaxWidth().height(56.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
//            shape = RoundedCornerShape(12.dp),
//            enabled = registerState !is RegisterState.Loading
//        ) {
//            if (registerState is RegisterState.Loading) {
//                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = TextWhite)
//            } else {
//                Text("Register", fontWeight = FontWeight.Bold, color = TextWhite)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        TextButton(onClick = { navController.navigate("login") }) {
//            Text("Already have an account? Sign In", color = PrimaryBlue)
//        }
//    }
//}
//
//// Helper function to get current date
//fun getCurrentDate(): String {
//    val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
//    return dateFormat.format(java.util.Date())
//}


package com.example.baustclubh.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.baustclubh.data.model.User
import com.example.baustclubh.ui.theme.*
import com.example.baustclubh.viewmodel.AuthViewModel
import com.example.baustclubh.viewmodel.RegisterState

@Composable
fun RegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var studentId by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }
    var batch by remember { mutableStateOf("") }
    var agreeToTerms by remember { mutableStateOf(false) }

    val registerState by authViewModel.registerState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(registerState) {
        when (registerState) {
            is RegisterState.Success -> {
                Toast.makeText(context, "Registration Successful! Please Login.", Toast.LENGTH_LONG).show()
                navController.navigate("login") {
                    popUpTo("register") { inclusive = true }
                }
                authViewModel.resetStates()
            }
            is RegisterState.Error -> {
                Toast.makeText(context, (registerState as RegisterState.Error).message, Toast.LENGTH_LONG).show()
                authViewModel.resetStates()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(horizontal = 24.dp)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Create Account", color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Join BAUST Club Hub", color = TextGray, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(24.dp))

        // Step Bar (Verify, Details, Done)
        RegistrationStepBar()

        Spacer(modifier = Modifier.height(24.dp))

        // Input Fields
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name", color = TextGray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = CardBackground,
                focusedBorderColor = PrimaryBlue,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            enabled = registerState !is RegisterState.Loading
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = studentId,
            onValueChange = { studentId = it },
            label = { Text("Student ID", color = TextGray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = CardBackground,
                focusedBorderColor = PrimaryBlue,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            enabled = registerState !is RegisterState.Loading
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = TextGray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = CardBackground,
                focusedBorderColor = PrimaryBlue,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            enabled = registerState !is RegisterState.Loading
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = department,
                onValueChange = { department = it },
                label = { Text("Dept.", color = TextGray) },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = CardBackground,
                    focusedBorderColor = PrimaryBlue,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite
                )
            )
            OutlinedTextField(
                value = batch,
                onValueChange = { batch = it },
                label = { Text("Batch", color = TextGray) },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = CardBackground,
                    focusedBorderColor = PrimaryBlue,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = TextGray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = CardBackground,
                focusedBorderColor = PrimaryBlue,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password", color = TextGray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = CardBackground,
                focusedBorderColor = PrimaryBlue,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Terms and Conditions
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = agreeToTerms,
                onCheckedChange = { agreeToTerms = it },
                colors = CheckboxDefaults.colors(checkedColor = PrimaryBlue)
            )
            Text(
                text = "I agree to the Terms of Service and Privacy Policy",
                color = TextGray,
                fontSize = 11.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Register Button
        Button(
            onClick = {
                when {
                    name.isEmpty() -> Toast.makeText(context, "Enter full name", Toast.LENGTH_SHORT).show()
                    studentId.isEmpty() -> Toast.makeText(context, "Enter student ID", Toast.LENGTH_SHORT).show()
                    password != confirmPassword -> Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT).show()
                    !agreeToTerms -> Toast.makeText(context, "Please agree to Terms", Toast.LENGTH_SHORT).show()
                    else -> {
                        val user = User(
                            studentId = studentId,
                            name = name,
                            email = email,
                            department = department,
                            batch = batch,
                            password = password,
                            role = "student",
                            registeredClubs = emptyList(),
                            joinDate = getCurrentDate()
                        )
                        authViewModel.register(user)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            shape = RoundedCornerShape(12.dp),
            enabled = registerState !is RegisterState.Loading
        ) {
            if (registerState is RegisterState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = TextWhite)
            } else {
                Text("Register", fontWeight = FontWeight.Bold, color = TextWhite)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("login") }) {
            Text("Already have an account? Sign In", color = PrimaryBlue)
        }
    }
}

@Composable
fun RegistrationStepBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        StepCircle("Verify", true, false)
        HorizontalDivider(modifier = Modifier.width(30.dp).padding(horizontal = 4.dp), thickness = 2.dp, color = PrimaryBlue)
        StepCircle("Details", false, true, "2")
        HorizontalDivider(modifier = Modifier.width(30.dp).padding(horizontal = 4.dp), thickness = 2.dp, color = CardBackground)
        StepCircle("Done", false, false, "3")
    }
}

@Composable
fun StepCircle(label: String, isDone: Boolean, isActive: Boolean, number: String = "") {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(if (isDone || isActive) PrimaryBlue else Color.Transparent, CircleShape)
                .border(1.dp, if (isDone || isActive) PrimaryBlue else TextGray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (isDone) {
                Icon(Icons.Default.Check, contentDescription = null, tint = TextWhite, modifier = Modifier.size(16.dp))
            } else {
                Text(number, color = if (isActive) TextWhite else TextGray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
        Text(label, color = if (isActive || isDone) PrimaryBlue else TextGray, fontSize = 10.sp)
    }
}

fun getCurrentDate(): String {
    val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
    return dateFormat.format(java.util.Date())
}