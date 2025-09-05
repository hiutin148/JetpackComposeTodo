package com.hiutin.jetpackcomposetodo.domain.models

import java.time.LocalDate
import java.time.LocalTime

data class Task(
    val id: String,
    val title: String?,
    val description: String?,
    val date: LocalDate?,
    val time: LocalTime?,
    val subtasks: List<SubTask> = emptyList(),
    val isDone: Boolean = false,
)