package com.hiutin.jetpackcomposetodo.ui.features.task.add_task.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hiutin.jetpackcomposetodo.R
import com.hiutin.jetpackcomposetodo.domain.models.SubTask
import com.hiutin.jetpackcomposetodo.ui.composables.AppTextField
import com.hiutin.jetpackcomposetodo.ui.composables.TouchableOpacity

@Composable
fun SubTaskSection(
    subtasks: List<SubTask>,
    onAddSubTask: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isAddingNewSubTask by remember { mutableStateOf(false) }
    var taskText by remember { mutableStateOf(TextFieldValue()) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isAddingNewSubTask) {
        if (isAddingNewSubTask) {
            focusRequester.requestFocus()
        }
    }

    Column(modifier) {
        if (isAddingNewSubTask) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 34.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppTextField(
                    value = taskText,
                    onTextChange = { taskText = it },
                    hint = stringResource(R.string.subtask_hint),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester)
                )

                IconButton(
                    enabled = !taskText.text.isEmpty(), onClick = {
                        val text = taskText.text.trim()
                        if (text.isNotEmpty()) {
                            onAddSubTask(text)
                            taskText = TextFieldValue() // Reset input
                            isAddingNewSubTask = false
                        }
                    }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.add_subtask),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                // Cancel button
                IconButton(
                    onClick = {
                        taskText = TextFieldValue()
                        isAddingNewSubTask = false
                    }) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = stringResource(R.string.cancel_adding_subtask),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

        }
        LazyColumn(modifier = Modifier.padding(start = 34.dp, bottom = 8.dp)) {
            items(subtasks) { subtask ->
                Text(
                    subtask.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        TouchableOpacity(
            enabled = !isAddingNewSubTask, onClick = { isAddingNewSubTask = true }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.AddTask,
                    contentDescription = stringResource(R.string.add_subtask)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.add_subtask),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }


    }
}