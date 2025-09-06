package com.hiutin.jetpackcomposetodo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(@PrimaryKey val id: String, val name: String, val color: String)