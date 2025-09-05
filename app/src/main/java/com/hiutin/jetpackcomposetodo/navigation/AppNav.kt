package com.hiutin.jetpackcomposetodo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hiutin.jetpackcomposetodo.ui.features.category.categories_list.CategoriesListScreen
import com.hiutin.jetpackcomposetodo.ui.features.task.add_task.AddTaskScreen
import com.hiutin.jetpackcomposetodo.ui.features.task.todolist.TodoListScreen

@Composable
fun AppNav( navController: NavHostController ,modifier: Modifier = Modifier) {

    NavHost(navController = navController, startDestination = AppRoute.TodoList.route) {
        composable(route = AppRoute.TodoList.route) {
            TodoListScreen(navController, modifier = Modifier)
        }

        composable(
            route = AppRoute.CategoriesList.route,
        ) {
            CategoriesListScreen(navController, modifier = Modifier)
        }

        composable(
            route = AppRoute.AddTask.route,
        ) {
            AddTaskScreen(navController, modifier = Modifier)
        }
    }
}

object NavigationKeys {
    const val KEY_TASK_SAVED_MESSAGE = "task_saved_message"
}
