package com.hiutin.jetpackcomposetodo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class CustomColors(
    val grey: Color,
)

val lightCustomColors = CustomColors(
    grey = grey,
)

val darkCustomColors = CustomColors(
    grey = grey,
)

val AppColors: CustomColors
    @Composable get() = if (isSystemInDarkTheme()) darkCustomColors else lightCustomColors