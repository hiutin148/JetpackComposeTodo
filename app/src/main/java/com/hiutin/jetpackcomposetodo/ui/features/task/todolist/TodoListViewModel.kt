package com.hiutin.jetpackcomposetodo.ui.features.task.todolist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiutin.jetpackcomposetodo.domain.models.Task
import com.hiutin.jetpackcomposetodo.domain.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject() constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    val tasks: StateFlow<List<Task>> = taskRepository.getTasks().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.deleteTask(task)
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "deleteTask: $e")
            }
        }
    }

    fun updateTaskStatus(taskId: String, isDone: Boolean, hasSubTask: Boolean) {
        viewModelScope.launch {
            try {
                taskRepository.updateTaskStatus(taskId, isDone)
                if (hasSubTask) {
                    taskRepository.updateAllSubtasksStatusByTask(taskId, isDone)
                }
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "updateTaskStatus: $e")
            }
        }
    }

    fun updateSubtaskStatus(subtaskId: String, isDone: Boolean) {
        viewModelScope.launch {
            try {
                taskRepository.updateSubtaskStatus(subtaskId, isDone)
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "updateSubtaskStatus: $e")
            }
        }
    }
}