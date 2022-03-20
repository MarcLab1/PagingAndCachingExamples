package com.googlebooksapi.ui.presentation.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.googlebooksapi.utils.Constants

@Composable
fun MyBottomNavigationBar(navController: NavController) {
    BottomNavigation() {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Constants.navItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(Route.First.route)
                        launchSingleTop = true
                    }
                },
                icon = {  },
                label = { Text(text = item.label) },
                alwaysShowLabel = true
            )
        }
    }
}

sealed class Route(val route: String, val label: String, val iv: ImageVector) {
    object First : Route("Custom", "Custom Pagination", Icons.Default.ThumbUp)
    object Second : Route("Paging3Search", "Paging 3 Pagination", Icons.Default.List)
    object Third : Route("Paging3Cache", "Paging 3 Cache", Icons.Default.List)
    object Fourth : Route("Databaseonly", "Database Only", Icons.Default.List)
}