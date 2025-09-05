package com.hiutin.jetpackcomposetodo.ui.features.task.add_task.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hiutin.jetpackcomposetodo.R
import com.hiutin.jetpackcomposetodo.ui.composables.AppButton

@Composable
fun ActionButtons(
    isEnabled: Boolean, onClockClick: () -> Unit, onSaveClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AppButton(
            onClick = onClockClick, enabled = isEnabled, contentPadding = PaddingValues(18.dp)
        ) {
            Icon(
                Icons.Default.Alarm,
                contentDescription = stringResource(R.string.add_task_choose_time),
                modifier = Modifier.size(20.dp)
            )
        }

        AppButton(
            onClick = onSaveClick,
            enabled = isEnabled,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(18.dp)
        ) {
            Text("Save")
        }
    }
}