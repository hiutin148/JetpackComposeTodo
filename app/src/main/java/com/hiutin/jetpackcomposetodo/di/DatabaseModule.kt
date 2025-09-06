package com.hiutin.jetpackcomposetodo.di

import android.content.Context
import androidx.room.Room
import com.hiutin.jetpackcomposetodo.data.local.AppDatabase
import com.hiutin.jetpackcomposetodo.data.local.PrepopulateCallback
import com.hiutin.jetpackcomposetodo.data.local.dao.CategoryDao
import com.hiutin.jetpackcomposetodo.data.local.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        prepopulateCallback: PrepopulateCallback
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-name"
        ).addCallback(prepopulateCallback).build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(appDatabase: AppDatabase): TaskDao {
        return appDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }
}