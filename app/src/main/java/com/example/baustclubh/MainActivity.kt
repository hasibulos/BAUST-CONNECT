//package com.example.baustclubh
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.navigation.compose.rememberNavController
//import com.example.baustclubh.ui.navigation.BAUSTNavGraph
//import com.example.baustclubh.ui.theme.BAUSTConnectTheme
//import com.example.baustclubh.utils.FirstTimeSetup
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val firstTimeSetup = FirstTimeSetup(this)
//
//        setContent {
//            BAUSTConnectTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    var isSetupComplete by remember { mutableStateOf(false) }
//                    var isChecking by remember { mutableStateOf(true) }
//
//                    LaunchedEffect(Unit) {
//                        CoroutineScope(Dispatchers.IO).launch {
//                            firstTimeSetup.checkAndSetup { success ->
//                                isSetupComplete = success
//                                isChecking = false
//                            }
//                        }
//                    }
//
//                    if (isChecking) {
//                        // Loading screen
//                        androidx.compose.material3.CircularProgressIndicator()
//                    } else {
//                        val navController = rememberNavController()
//                        BAUSTNavGraph(navController = navController)
//                    }
//                }
//            }
//        }
//    }
//}

package com.example.baustclubh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.baustclubh.ui.navigation.BAUSTNavGraph
import com.example.baustclubh.ui.theme.BAUSTConnectTheme
import com.example.baustclubh.utils.FirstTimeSetup

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firstTimeSetup = FirstTimeSetup(this)

        setContent {
            // আমাদের ডাইনামিক লাইট/ডার্ক থিম দিয়ে পুরো অ্যাপ র‍্যাপ করা হলো
            BAUSTConnectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background // থিম অনুযায়ী অটো ব্যাকগ্রাউন্ড চেঞ্জ হবে
                ) {
                    var isSetupComplete by remember { mutableStateOf(false) }
                    var isChecking by remember { mutableStateOf(true) }

                    // ব্যাকগ্রাউন্ড টাস্ক হ্যান্ডেল করার সঠিক কম্পোজ স্ট্যান্ডার্ড উপায়
                    LaunchedEffect(Unit) {
                        firstTimeSetup.checkAndSetup { success ->
                            isSetupComplete = success
                            isChecking = false
                        }
                    }

                    if (isChecking) {
                        // লোডিং ইন্ডিকেটরটিকে স্ক্রিনের একদম মাঝখানে নিয়ে আসা হলো
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary // থিমের প্রাইমারি ব্লু কালার পাবে
                            )
                        }
                    } else {
                        val navController = rememberNavController()
                        BAUSTNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}