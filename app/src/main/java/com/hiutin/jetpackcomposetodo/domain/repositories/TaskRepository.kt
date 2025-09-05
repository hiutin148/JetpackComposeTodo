package com.hiutin.jetpackcomposetodo.domain.repositories

import com.hiutin.jetpackcomposetodo.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun saveTask(task: Task)
    suspend fun getTask(taskId: String): Task?
    suspend fun updateTaskStatus(taskId: String, isDone: Boolean)
    suspend fun updateSubtaskStatus(subtaskId: String, isDone: Boolean)
    suspend fun updateAllSubtasksStatusByTask(taskId: String, isDone: Boolean)
    suspend fun deleteTask(task: Task)
    fun getTasks(): Flow<List<Task>>
}
