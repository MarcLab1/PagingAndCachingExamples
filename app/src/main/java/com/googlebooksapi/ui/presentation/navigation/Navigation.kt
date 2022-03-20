package com.googlebooksapi.ui.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.googlebooksapi.ui.presentation.databaseonly.DatabaseOnlyPage
import com.googlebooksapi.ui.presentation.paging3search.BooksPagination3SearchPage

import com.googlebooksapi.ui.presentation.navigation.Route
import com.googlebooksapi.ui.presentation.paging3cache.Paging3CachePage

@Composable
fun Navigation(nav : NavController) {

    NavHost(navController = nav as NavHostController, startDestination = Route.First.route)
    {
        composable(Route.First.route)
        {
            CustomPagination(nav = nav)
        }
        composable(Route.Second.route)
        {
            BooksPagination3SearchPage(nav = nav)
        }
        composable(Route.Third.route)
        {
            Paging3CachePage(nav = nav)
        }
        composable(Route.Fourth.route)
        {
            DatabaseOnlyPage(nav = nav)
        }
        composable("volume/{volumeId}")
        {
            val volumeId = it.arguments?.getString("volumeId")
            if (volumeId != null) {
                BookDetailPage()
            }
        }
    }
}
