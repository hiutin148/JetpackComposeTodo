package com.hiutin.jetpackcomposetodo.ui.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun TouchableOpacity(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (pressed) 0.4f else 1f,
        label = "alphaAnim"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                this.alpha = alpha
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        if (!enabled) return@detectTapGestures
                        pressed = true
                        tryAwaitRelease() // chờ khi user nhả tay
                        pressed = false
                    },
                    onTap = { onClick() }
                )
            }
    ) {
        content()
    }
}
