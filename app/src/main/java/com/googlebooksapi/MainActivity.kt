package com.googlebooksapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.navigation.compose.rememberNavController
import com.googlebooksapi.ui.presentation.Navigation
import com.googlebooksapi.ui.presentation.navigation.MyBottomNavigationBar
import com.googlebooksapi.ui.theme.GoogleBooksApiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoogleBooksApiTheme {
                Surface(color = MaterialTheme.colors.background) {

                    var scaffoldState = rememberScaffoldState()
                    var nav = rememberNavController()
                    Scaffold(
                        scaffoldState = scaffoldState,
                        content = { Navigation(nav) },
                        bottomBar = { MyBottomNavigationBar(nav)}
                    )
                }
            }
        }
    }
}

