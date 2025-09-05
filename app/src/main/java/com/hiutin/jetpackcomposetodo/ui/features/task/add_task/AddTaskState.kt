package com.hiutin.jetpackcomposetodo.ui.features.task.add_task

sealed class AddTaskState {
    object Idle : AddTaskState()
    object Saving : AddTaskState()
    object Success : AddTaskState()
    data class Error(val message: String) : AddTaskState()
}