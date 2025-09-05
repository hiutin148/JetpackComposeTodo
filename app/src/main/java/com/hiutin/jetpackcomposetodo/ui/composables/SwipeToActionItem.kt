package com.hiutin.jetpackcomposetodo.ui.composables

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableDefaults
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

enum class SwipeState {
    COLLAPSED,
    REVEALED
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeToActionItem(
    modifier: Modifier = Modifier,
    actionWidth: Dp = 96.dp,
    actions: @Composable RowScope.() -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val density = LocalDensity.current
    val anchors = remember(density) {
        DraggableAnchors {
            SwipeState.COLLAPSED at 0f
            SwipeState.REVEALED at with(density) { -actionWidth.toPx() }
        }
    }

    // --- CẤU TRÚC ĐÚNG DỰA TRÊN MÃ NGUỒN BẠN CUNG CẤP ---

    // BƯỚC 1: Khởi tạo State một cách đơn giản nhất.
    // Không có bất kỳ tham số vật lý nào ở đây.
    val state = remember {
        AnchoredDraggableState(
            initialValue = SwipeState.COLLAPSED,
            anchors = anchors
        )
    }

    // BƯỚC 2: Tạo flingBehavior bằng hàm @Composable của AnchoredDraggableDefaults.
    // Hàm này nhận positionalThreshold và animationSpec.
    val flingBehavior = AnchoredDraggableDefaults.flingBehavior(
        state = state,
        positionalThreshold = { totalDistance -> totalDistance * 0.5f },
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        // Lớp hành động (phía sau)
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            content = actions
        )

        // Lớp nội dung (phía trước)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset {
                    IntOffset(x = state.requireOffset().roundToInt(), y = 0)
                }
                // BƯỚC 3: Truyền state và flingBehavior đã tạo vào modifier.
                .anchoredDraggable(
                    state = state,
                    orientation = Orientation.Horizontal,
                    flingBehavior = flingBehavior
                )
                .background(MaterialTheme.colorScheme.surface),
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}