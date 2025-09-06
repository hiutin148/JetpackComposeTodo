package com.hiutin.jetpackcomposetodo.data.repositories

import com.hiutin.jetpackcomposetodo.data.local.dao.CategoryDao
import com.hiutin.jetpackcomposetodo.data.mappers.toEntity
import com.hiutin.jetpackcomposetodo.data.mappers.toModel
import com.hiutin.jetpackcomposetodo.domain.models.Category
import com.hiutin.jetpackcomposetodo.domain.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject() constructor(val categoryDao: CategoryDao) :
    CategoryRepository {
    override suspend fun addCategory(category: Category) {
        categoryDao.insertCategory(category.toEntity())
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories().map { categories -> categories.map { it.toModel() } }
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category.toEntity())
    }
}