package com.hiutin.jetpackcomposetodo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.hiutin.jetpackcomposetodo.data.local.entities.SubTaskEntity
import com.hiutin.jetpackcomposetodo.data.local.entities.TaskEntity
import com.hiutin.jetpackcomposetodo.data.local.entities.TaskWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubTasks(subTasks: List<SubTaskEntity>)

    @Transaction
    suspend fun insertTaskWithSubtasks(task: TaskEntity, subtasks: List<SubTaskEntity>) {
        insertTask(task)
        if (subtasks.isNotEmpty()) {
            insertSubTasks(subtasks.map { it.copy(taskId = task.id) })
        }
    }

    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTask(taskId: String): TaskWithDetails?

    @Transaction
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskWithDetails>>

    @Query("UPDATE tasks SET is_done = :isDone WHERE id = :taskId")
    suspend fun updateTaskStatus(taskId: String, isDone: Boolean)

    @Query("UPDATE subtasks SET is_done = :isDone WHERE id = :subtaskId")
    suspend fun updateSubtaskStatus(subtaskId: String, isDone: Boolean)

    @Query("UPDATE subtasks SET is_done = :isDone WHERE task_id = :taskId")
    suspend fun updateAllSubtasksStatusByTask(taskId: String, isDone: Boolean)

    @Delete
    suspend fun delete(taskEntity: TaskEntity)

    @Transaction
    @Query("SELECT * FROM tasks WHERE category_id = :cateId")
    suspend fun getTasksByCategory(cateId: String): List<TaskWithDetails>
}
