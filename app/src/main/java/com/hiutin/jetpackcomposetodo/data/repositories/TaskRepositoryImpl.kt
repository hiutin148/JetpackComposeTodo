package com.hiutin.jetpackcomposetodo.data.repositories

import com.hiutin.jetpackcomposetodo.data.local.dao.TaskDao
import com.hiutin.jetpackcomposetodo.data.mappers.toEntity
import com.hiutin.jetpackcomposetodo.data.mappers.toModel
import com.hiutin.jetpackcomposetodo.domain.models.Task
import com.hiutin.jetpackcomposetodo.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject() constructor(val taskDao: TaskDao) : TaskRepository {
    override suspend fun saveTask(task: Task) {
        taskDao.insertTaskWithSubtasks(
            task.toEntity(), task.subtasks.map { it.toEntity(task.id) })
    }

    override suspend fun getTask(taskId: String): Task? {
        return taskDao.getTaskWithSubtasks(taskId)?.toModel()
    }

    override suspend fun updateTaskStatus(taskId: String, isDone: Boolean) {
        taskDao.updateTaskStatus(taskId, isDone)
    }

    override suspend fun updateSubtaskStatus(subtaskId: String, isDone: Boolean) {
        taskDao.updateSubtaskStatus(subtaskId, isDone)
    }

    override suspend fun updateAllSubtasksStatusByTask(
        taskId: String, isDone: Boolean
    ) {
        taskDao.updateAllSubtasksStatusByTask(taskId, isDone)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.delete(task.toEntity())
    }

    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getAllTasksWithSubtasks().map { tasks -> tasks.map { it.toModel() } }
    }
}