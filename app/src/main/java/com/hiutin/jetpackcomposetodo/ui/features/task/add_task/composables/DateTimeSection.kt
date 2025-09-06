package com.hiutin.jetpackcomposetodo.ui.features.task.add_task.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hiutin.jetpackcomposetodo.R
import com.hiutin.jetpackcomposetodo.common.extensions.toDateString
import com.hiutin.jetpackcomposetodo.common.extensions.toTimeString
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun DateTimeSection(
    date: LocalDate?,
    time: LocalTime?,
    needRemind: Boolean,
    onChangeNeedRemind: (Boolean) -> Unit
) {
    Column(modifier = Modifier.padding(bottom = 4.dp)) {
        Text(
            buildString {
                append("Time: ")
                date?.let { append(it.toDateString()).append(" ") }
                time?.let { append(it.toTimeString()) }
            },
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
        if (time != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = needRemind,
                    onCheckedChange = onChangeNeedRemind
                )
                Text(
                    stringResource(R.string.remind_me),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}