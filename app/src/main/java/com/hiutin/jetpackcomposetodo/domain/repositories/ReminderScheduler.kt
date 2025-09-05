package com.hiutin.jetpackcomposetodo.domain.repositories

import com.hiutin.jetpackcomposetodo.domain.models.Task

interface ReminderScheduler {
    fun schedule(task: Task)
    fun cancel(task: Task)
}