package com.hiutin.jetpackcomposetodo.ui.features.task.todolist

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hiutin.jetpackcomposetodo.navigation.NavigationKeys
import com.hiutin.jetpackcomposetodo.ui.features.task.todolist.composables.TaskList
import com.hiutin.jetpackcomposetodo.ui.features.todolist.composables.DateHeader
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    todoListViewModel: TodoListViewModel = hiltViewModel()
) {
    val mockCategories = remember { (0..3).toList() }

    val currentDate = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM", Locale.getDefault()))
        } else {
            val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
            dateFormat.format(Date())
        }
    }

    val taskList by todoListViewModel.tasks.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val taskSavedMessage = savedStateHandle?.get<String>(NavigationKeys.KEY_TASK_SAVED_MESSAGE)

    LaunchedEffect(taskSavedMessage) {
        if (taskSavedMessage != null) {
            snackbarHostState.showSnackbar(
                message = taskSavedMessage, duration = SnackbarDuration.Short
            )
            savedStateHandle.remove<String>(NavigationKeys.KEY_TASK_SAVED_MESSAGE)
        }
    }
    val context = LocalContext.current
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = 24.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            DateHeader(currentDate = currentDate)
//            CategoryGrid(categories = mockCategories)
            TaskList(
                taskList,
                onDeleteItem = todoListViewModel::deleteTask,
                onTaskCheck = todoListViewModel::updateTaskStatus,
                onSubTaskCheck = todoListViewModel::updateSubtaskStatus,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TodoListPreview() {
    MaterialTheme {
        val navController = rememberNavController()
        TodoListScreen(
            navController = navController, modifier = Modifier.fillMaxSize()
        )
    }
}