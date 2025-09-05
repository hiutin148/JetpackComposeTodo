package com.hiutin.jetpackcomposetodo.ui.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hiutin.jetpackcomposetodo.navigation.AppNav
import com.hiutin.jetpackcomposetodo.navigation.AppRoute
import com.hiutin.jetpackcomposetodo.ui.composables.RequestAppPermissions
import com.hiutin.jetpackcomposetodo.ui.features.todolist.composables.AddTaskFab

@Composable
fun MainScreen() {
    val bottomScreens = listOf(
        AppRoute.TodoList,
        AppRoute.CategoriesList,
    )
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars),
        floatingActionButton = {
            if (currentDestination?.route == AppRoute.TodoList.route) AddTaskFab(
                onClick = {
                    navController.navigate(AppRoute.AddTask.route)
                })
        },
        bottomBar = {
            if (bottomScreens.any { it.route == currentDestination?.route }) {
                NavigationBar {
                    bottomScreens.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                screen.icon?.let {
                                    Icon(
                                        imageVector = it, contentDescription = null
                                    )
                                }
                            },
                            label = { Text(screen.title.orEmpty()) },
                            selected = currentDestination?.route == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            })
                    }
                }
            }
        }) { innerPadding ->
        AppNav(navController, modifier = Modifier.padding(innerPadding))
        RequestAppPermissions()
    }
}