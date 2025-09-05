package com.hiutin.jetpackcomposetodo.domain.models

data class SubTask(
    val id: String,
    val title: String,
    val isDone: Boolean = false
)