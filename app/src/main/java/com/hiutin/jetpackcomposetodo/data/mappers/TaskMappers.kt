package com.hiutin.jetpackcomposetodo.data.mappers

import com.hiutin.jetpackcomposetodo.data.helpers.Converters
import com.hiutin.jetpackcomposetodo.data.local.entities.SubTaskEntity
import com.hiutin.jetpackcomposetodo.data.local.entities.TaskEntity
import com.hiutin.jetpackcomposetodo.data.local.entities.TaskWithSubtasks
import com.hiutin.jetpackcomposetodo.domain.models.SubTask
import com.hiutin.jetpackcomposetodo.domain.models.Task

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        date = Converters.fromLocalDate(this.date),
        time = Converters.fromLocalTime(this.time),
        isDone = this.isDone,
    )
}

fun SubTask.toEntity(taskId: String): SubTaskEntity {
    return SubTaskEntity(
        id = id,
        taskId = taskId,
        title = title,
        isDone = isDone,
    )
}

fun TaskWithSubtasks.toModel(): Task {
    return Task(
        id = task.id,
        title = task.title.orEmpty(),
        description = task.description.orEmpty(),
        date = Converters.toLocalDate(task.date),
        time = Converters.toLocalTime(task.time),
        isDone = task.isDone,
        subtasks = subtasks.map {
            SubTask(
                id = it.id,
                title = it.title,
                isDone = it.isDone
            )
        }
    )
}