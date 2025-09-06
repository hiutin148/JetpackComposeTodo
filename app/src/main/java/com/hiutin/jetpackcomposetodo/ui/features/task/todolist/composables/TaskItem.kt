package com.hiutin.jetpackcomposetodo.ui.features.task.todolist.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hiutin.jetpackcomposetodo.R
import com.hiutin.jetpackcomposetodo.domain.models.Task
import com.hiutin.jetpackcomposetodo.ui.composables.SwipeToActionItem
import com.hiutin.jetpackcomposetodo.ui.composables.TouchableOpacity

@Composable
fun TaskItem(
    task: Task,
    onTap: (() -> Unit)? = null,
    onTaskCheck: ((String, Boolean, Boolean) -> Unit)? = null,
    onSubTaskCheck: ((String, Boolean) -> Unit)? = null,
    onDelete: (() -> Unit)? = null
) {
    TouchableOpacity(onClick = { onTap?.invoke() }) {
        SwipeToActionItem(
            actionWidth = 60.dp, actions = {
                IconButton(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(Color.Red)
                        .size(60.dp),
                    onClick = { onDelete?.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete),
                        tint = Color.White
                    )
                }
            }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            color = Color.Black.copy(alpha = 0.15f),
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    }
                    .padding(top = 6.dp, start = 2.dp, bottom = 6.dp, end = 16.dp)) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Checkbox(
                        checked = task.isDone,
                        onCheckedChange = {
                            onTaskCheck?.invoke(
                                task.id,
                                it,
                                !task.subtasks.isEmpty()
                            )
                        },
                        modifier = Modifier.padding(0.dp)
                    )
                    TaskTitle(
                        text = task.title.orEmpty()
                    )
                }
                if (task.category != null)
                    Box(
                        Modifier.padding(start = 48.dp)
                    ) {
                        Text(
                            task.category.name,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = task.category.color,
                            modifier = Modifier
                                .background(color = task.category.color.copy(alpha = 0.1f))
                        )
                    }

                Column(modifier = Modifier.padding(start = 48.dp)) {
                    task.subtasks.forEachIndexed { index, subTask ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Checkbox(
                                checked = subTask.isDone,
                                onCheckedChange = { onSubTaskCheck?.invoke(subTask.id, it) },
                                modifier = Modifier.padding(0.dp)
                            )
                            TaskTitle(
                                text = subTask.title
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.SemiBold,
    )
}
