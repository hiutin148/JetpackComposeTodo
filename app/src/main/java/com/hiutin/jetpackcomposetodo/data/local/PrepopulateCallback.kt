package com.hiutin.jetpackcomposetodo.data.local

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hiutin.jetpackcomposetodo.data.local.dao.CategoryDao
import com.hiutin.jetpackcomposetodo.data.local.entities.CategoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class PrepopulateCallback @Inject constructor(
    private val categoryDaoProvider: Provider<CategoryDao>
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            populateDatabase()
        }
    }

    private suspend fun populateDatabase() {
        val defaultCategories = listOf(
            CategoryEntity(id = "cate0", name = "Health", color = "#7990F8"),
            CategoryEntity(id = "cate1", name = "Work", color = "#46CF8B"),
            CategoryEntity(id = "cate2", name = "Family", color = "#BC5EAD")
        )
        categoryDaoProvider.get().insertAll(defaultCategories)
    }
}