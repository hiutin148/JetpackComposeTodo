package com.hiutin.jetpackcomposetodo.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "first_name") val title: String?,
    @ColumnInfo(name = "last_name") val description: String?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "time") val time: String?,
    @ColumnInfo(name = "is_done") val isDone: Boolean = false
)