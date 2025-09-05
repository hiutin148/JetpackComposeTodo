package com.hiutin.jetpackcomposetodo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Task
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppRoute(val route: String, val title: String? = null, val icon: ImageVector? = null) {
    object TodoList : AppRoute("todo_list", "Home", Icons.Default.Task)
    object CategoriesList : AppRoute("categories_list", "Category", Icons.Default.Category)
    object AddTask : AppRoute("add_task")
}