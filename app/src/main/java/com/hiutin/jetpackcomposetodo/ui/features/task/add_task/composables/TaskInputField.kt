package com.hiutin.jetpackcomposetodo.ui.features.task.add_task.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hiutin.jetpackcomposetodo.R
import com.hiutin.jetpackcomposetodo.domain.models.SubTask
import com.hiutin.jetpackcomposetodo.ui.composables.AppTextField

@Composable
fun TaskInputField(
    taskText: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    subtasks: List<SubTask>,
    onAddSubTask: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        AppTextField(
            value = taskText,
            onTextChange = onTextChange,
            hint = stringResource(R.string.add_new_task_hint)
        )

        if (!taskText.text.isEmpty())
            SubTaskSection(subtasks, onAddSubTask, modifier = Modifier.padding(top = 32.dp))
    }
}