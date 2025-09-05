package com.hiutin.jetpackcomposetodo.ui.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.hiutin.jetpackcomposetodo.R
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickDateTimeDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onDateTimePicked: (LocalDate, LocalTime?) -> Unit
) {
    if (show) {
        // It's better to remember the date outside the 'when' so it's not reset.
        var step by remember { mutableStateOf("date") }
        val dateState = rememberDatePickerState()
        val timeState = rememberTimePickerState(initialHour = 6, initialMinute = 0)

        when (step) {
            "date" -> {
                DatePickerDialog(
                    onDismissRequest = onDismiss,
                    confirmButton = {
                        TextButton(
                            enabled = dateState.selectedDateMillis != null,
                            onClick = {
                                // Proceed to time selection only if a date is actually selected.
                                if (dateState.selectedDateMillis != null) {
                                    step = "time"
                                } else {
                                    onDismiss() // Or show a message to the user
                                }
                            }) {
                            Text(stringResource(R.string.continue_text))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = onDismiss) {
                            Text(stringResource(R.string.cancel))
                        }
                    },
                    properties = DialogProperties(usePlatformDefaultWidth = false)
                ) {
                    DatePicker(state = dateState)
                }
            }

            "time" -> {
                // Use AlertDialog to host the TimePicker here as well.
                AlertDialog(
                    onDismissRequest = {
                        // This block is for when the user clicks outside the dialog.
                        // We treat it as "skip time".
                        val millis = dateState.selectedDateMillis
                        if (millis != null) {
                            val date = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            onDateTimePicked(date, null)
                        }
                        onDismiss()
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            val millis = dateState.selectedDateMillis
                            if (millis != null) {
                                val date = Instant.ofEpochMilli(millis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                                val time = LocalTime.of(timeState.hour, timeState.minute)
                                onDateTimePicked(date, time)
                            }
                            onDismiss()
                        }) {
                            Text(stringResource(R.string.ok))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            val millis = dateState.selectedDateMillis
                            if (millis != null) {
                                val date = Instant.ofEpochMilli(millis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                                onDateTimePicked(date, null) // User explicitly skips time
                            }
                            onDismiss()
                        }) {
                            Text(stringResource(R.string.skip))
                        }
                    },
                    text = {
                        TimePicker(state = timeState)
                    }
                )
            }
        }
    }
}
