package com.hiutin.jetpackcomposetodo.common.extensions

import androidx.compose.ui.graphics.Color

fun Color.toHex(includeAlpha: Boolean = true): String {
    val a = (alpha * 255).toInt()
    val r = (red * 255).toInt()
    val g = (green * 255).toInt()
    val b = (blue * 255).toInt()
    return if (includeAlpha) {
        String.format("#%02X%02X%02X%02X", a, r, g, b) // AARRGGBB
    } else {
        String.format("#%02X%02X%02X", r, g, b) // RRGGBB
    }
}