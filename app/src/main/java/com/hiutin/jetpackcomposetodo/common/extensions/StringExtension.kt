package com.hiutin.jetpackcomposetodo.common.extensions

import androidx.core.graphics.toColorInt
import androidx.compose.ui.graphics.Color as ComposeColor

fun String.toAndroidColor(): Int {
    val hex = this.removePrefix("#")
    val formattedHex = when (hex.length) {
        6 -> "#$hex"       // RRGGBB
        8 -> "#$hex"       // AARRGGBB
        else -> throw IllegalArgumentException("Hex color không hợp lệ: $this")
    }
    return formattedHex.toColorInt()
}

fun String.toComposeColor(): ComposeColor {
    val colorInt = this.toAndroidColor()
    return ComposeColor(colorInt)
}
