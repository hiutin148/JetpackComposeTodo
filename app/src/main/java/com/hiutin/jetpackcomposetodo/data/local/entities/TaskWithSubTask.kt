package com.hiutin.jetpackcomposetodo.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class TaskWithSubtasks(
    @Embedded val task: TaskEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "task_id"
    )
    val subtasks: List<SubTaskEntity>
)
