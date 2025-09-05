package com.hiutin.jetpackcomposetodo.ui.composables

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@Composable
fun DismissKeyboardOnTapOutside(
    content: @Composable () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    // 1) Clear focus để TextField mất focus
                    focusManager.clearFocus()
                    // 2) (Tuỳ chọn) Ẩn keyboard phòng trường hợp OEM/IME không tắt
                    keyboardController?.hide()
                })
            }
    ) {
        content()
    }
}
