package com.hiutin.jetpackcomposetodo.di

import android.content.Context
import com.hiutin.jetpackcomposetodo.data.repositories.CategoryRepositoryImpl
import com.hiutin.jetpackcomposetodo.data.repositories.ReminderSchedulerImpl
import com.hiutin.jetpackcomposetodo.data.repositories.TaskRepositoryImpl
import com.hiutin.jetpackcomposetodo.domain.repositories.CategoryRepository
import com.hiutin.jetpackcomposetodo.domain.repositories.ReminderScheduler
import com.hiutin.jetpackcomposetodo.domain.repositories.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindModule {
    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository
}


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideReminderScheduler(@ApplicationContext context: Context): ReminderScheduler =
        ReminderSchedulerImpl(context)
}


