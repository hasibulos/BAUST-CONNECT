//package com.example.baustclubh
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Surface
//import androidx.compose.ui.Modifier
//import androidx.navigation.compose.rememberNavController
//import com.example.baustclubh.ui.navigation.BAUSTNavGraph
//import com.example.baustclubh.ui.theme.BAUSTConnectTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            // সঠিক থিম কল করা হয়েছে
//            BAUSTConnectTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    val navController = rememberNavController()
//                    BAUSTNavGraph(navController = navController)
//                }
//            }
//        }
//    }
//}

package com.example.baustclubh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.baustclubh.ui.navigation.BAUSTNavGraph
import com.example.baustclubh.ui.theme.BAUSTConnectTheme
import com.example.baustclubh.utils.FirstTimeSetup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firstTimeSetup = FirstTimeSetup(this)

        setContent {
            BAUSTConnectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var isSetupComplete by remember { mutableStateOf(false) }
                    var isChecking by remember { mutableStateOf(true) }

                    LaunchedEffect(Unit) {
                        CoroutineScope(Dispatchers.IO).launch {
                            firstTimeSetup.checkAndSetup { success ->
                                isSetupComplete = success
                                isChecking = false
                            }
                        }
                    }

                    if (isChecking) {
                        // Loading screen
                        androidx.compose.material3.CircularProgressIndicator()
                    } else {
                        val navController = rememberNavController()
                        BAUSTNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}