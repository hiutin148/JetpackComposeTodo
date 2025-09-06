package com.hiutin.jetpackcomposetodo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hiutin.jetpackcomposetodo.data.helpers.Converters
import com.hiutin.jetpackcomposetodo.data.local.dao.CategoryDao
import com.hiutin.jetpackcomposetodo.data.local.dao.TaskDao
import com.hiutin.jetpackcomposetodo.data.local.entities.CategoryEntity
import com.hiutin.jetpackcomposetodo.data.local.entities.SubTaskEntity
import com.hiutin.jetpackcomposetodo.data.local.entities.TaskEntity

@Database(
    entities = [TaskEntity::class, SubTaskEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao
}