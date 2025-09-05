package com.hiutin.jetpackcomposetodo.ui.features.todolist.composables

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.hiutin.jetpackcomposetodo.domain.models.Task

@Composable
fun TaskList(
    taskList: List<Task>,
    onDeleteItem: (task: Task) -> Unit,
    onTaskCheck: ((String ,Boolean, Boolean) -> Unit)? = null,
    onSubTaskCheck: ((String, Boolean) -> Unit)? = null,
) {
    LazyColumn {
        items(taskList, key = { task -> task.id }) { task ->
            TaskItem(
                task,
                onDelete = { onDeleteItem(task) },
                onTap = { Log.d("TAG", "TaskList: $task") },
                onTaskCheck = onTaskCheck,
                onSubTaskCheck = onSubTaskCheck,
            )
        }
    }
}