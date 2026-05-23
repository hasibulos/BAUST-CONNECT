package com.example.baustclubh.ui.screens.admin

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baustclubh.data.model.User
import com.example.baustclubh.data.repository.AdminRepository
import com.example.baustclubh.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAdminScreen(navController: NavController) {
    val context = LocalContext.current
    val repository = remember { AdminRepository(context) }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var selectedRole by remember { mutableStateOf("Select Role") }
    var selectedDept by remember { mutableStateOf("Select Department") }
    var targetClubId by remember { mutableStateOf("") }

    var isRoleExpanded by remember { mutableStateOf(false) }
    var isDeptExpanded by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val roles = listOf("dept_admin", "club_admin")
    val departments = listOf("CSE", "EEE", "ME", "IPE", "CE", "BBA", "English")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Admin", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    AdminInputField(value = name, onValueChange = { name = it }, label = "Full Name")
                    AdminInputField(value = email, onValueChange = { email = it }, label = "Email Address")
                    AdminInputField(value = password, onValueChange = { password = it }, label = "Password")

                    // --- Role Dropdown ---
                    Box {
                        OutlinedButton(onClick = { isRoleExpanded = true }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp)) {
                            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                Text(selectedRole, color = MaterialTheme.colorScheme.onBackground)
                                Icon(Icons.Default.ArrowDropDown, null, tint = MaterialTheme.colorScheme.onBackground)
                            }
                        }
                        DropdownMenu(expanded = isRoleExpanded, onDismissRequest = { isRoleExpanded = false }) {
                            roles.forEach { role ->
                                DropdownMenuItem(text = { Text(role.replace("_", " ").uppercase()) }, onClick = { selectedRole = role; isRoleExpanded = false })
                            }
                        }
                    }

                    // --- Department Admin হলে এটি দেখাবে ---
                    if (selectedRole == "dept_admin") {
                        Box {
                            OutlinedButton(onClick = { isDeptExpanded = true }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp)) {
                                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                    Text(selectedDept, color = MaterialTheme.colorScheme.onBackground)
                                    Icon(Icons.Default.ArrowDropDown, null, tint = MaterialTheme.colorScheme.onBackground)
                                }
                            }
                            DropdownMenu(expanded = isDeptExpanded, onDismissRequest = { isDeptExpanded = false }) {
                                departments.forEach { dept ->
                                    DropdownMenuItem(text = { Text(dept) }, onClick = { selectedDept = dept; isDeptExpanded = false })
                                }
                            }
                        }
                    }

                    // --- Club Admin হলে এটি দেখাবে ---
                    if (selectedRole == "club_admin") {
                        AdminInputField(value = targetClubId, onValueChange = { targetClubId = it }, label = "Target Club ID")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && selectedRole != "Select Role") {
                                isLoading = true

                                // আপনার নতুন User মডেলের প্রোপ্রার্টি নেম অনুযায়ী অবজেক্ট ম্যাপ করা হলো
                                val newAdmin = User(
                                    studentId = "", // AdminRepository-তে এটি Firebase UID দিয়ে রিপ্লেস হবে
                                    name = name,
                                    email = email,
                                    password = password,
                                    role = selectedRole,
                                    department = if (selectedRole == "dept_admin") selectedDept else "",
                                    managedDept = if (selectedRole == "dept_admin") selectedDept else null,
                                    managedClub = if (selectedRole == "club_admin") targetClubId else null,
                                    joinDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
                                )

                                repository.createAdminAccount(newAdmin, password) { success, message ->
                                    isLoading = false
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    if (success) {
                                        name = ""; email = ""; password = ""
                                        selectedRole = "Select Role"; selectedDept = "Select Department"; targetClubId = ""
                                    }
                                }
                            } else {
                                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        enabled = !isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        if (isLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        else Text("Create Admin Account", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}