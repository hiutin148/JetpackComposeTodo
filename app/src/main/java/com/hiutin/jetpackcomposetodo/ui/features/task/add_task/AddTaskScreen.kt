package com.hiutin.jetpackcomposetodo.ui.features.task.add_task

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hiutin.jetpackcomposetodo.R
import com.hiutin.jetpackcomposetodo.navigation.NavigationKeys
import com.hiutin.jetpackcomposetodo.ui.composables.PickDateTimeDialog
import com.hiutin.jetpackcomposetodo.ui.features.task.add_task.composables.ActionButtons
import com.hiutin.jetpackcomposetodo.ui.features.task.add_task.composables.AddTaskHeader
import com.hiutin.jetpackcomposetodo.ui.features.task.add_task.composables.CategorySelectSection
import com.hiutin.jetpackcomposetodo.ui.features.task.add_task.composables.DateTimeSection
import com.hiutin.jetpackcomposetodo.ui.features.task.add_task.composables.TaskInputField
import com.hiutin.jetpackcomposetodo.ui.theme.JetpackComposeTodoTheme

@Composable
fun AddTaskScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AddTaskViewModel = hiltViewModel()
) {
    val taskText = viewModel.taskText
    val date = viewModel.date
    val time = viewModel.time
    val needRemind = viewModel.needRemind
    val selectedCategory = viewModel.selectedCategory
    val subtasks = viewModel.subtasks.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showDateTime by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val categories = viewModel.categories.collectAsState()

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is AddTaskState.Success -> {
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    NavigationKeys.KEY_TASK_SAVED_MESSAGE,
                    context.getString(R.string.add_task_success)
                )

                navController.popBackStack()
            }

            is AddTaskState.Error -> {
                snackbarHostState.showSnackbar(message = state.message)
                viewModel.resetState()
            }

            else -> {}
        }
    }

    val handleClockPressed = remember {
        {
            showDateTime = true
        }
    }

    Scaffold(
        modifier = modifier.imePadding(),
        snackbarHost = { SnackbarHost(snackbarHostState) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AddTaskHeader(onCloseClick = {
                navController.popBackStack()
            })

            Spacer(modifier = Modifier.height(56.dp))

            TaskInputField(
                taskText = taskText,
                onTextChange = { viewModel.onTextChange(it) },
                subtasks = subtasks.value,
                onAddSubTask = { viewModel.addSubTask(it) },
                modifier = Modifier.weight(1f)
            )

            if (date != null || time != null) {
                DateTimeSection(date, time, needRemind, viewModel::changeNeedRemind)
            }

            if (!taskText.text.isEmpty())
                CategorySelectSection(
                    categories.value,
                    viewModel::selectCategory,
                    selectedCategory
                )

            ActionButtons(
                isEnabled = taskText.text.isNotBlank() && uiState !is AddTaskState.Saving,
                onClockClick = handleClockPressed,
                onSaveClick = viewModel::saveTask
            )

            PickDateTimeDialog(
                show = showDateTime,
                onDismiss = { showDateTime = false },
                onDateTimePicked = viewModel::setDateTime
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddTaskScreenPreview() {
    JetpackComposeTodoTheme {
        AddTaskScreen(
            navController = rememberNavController(), modifier = Modifier.fillMaxSize()
        )
    }
}