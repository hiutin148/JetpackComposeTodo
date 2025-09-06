package com.hiutin.jetpackcomposetodo.domain.repositories

import com.hiutin.jetpackcomposetodo.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun addCategory(category: Category)
    fun getAllCategories(): Flow<List<Category>>
    suspend fun deleteCategory(category: Category)
}