package com.hiutin.jetpackcomposetodo.ui.composables

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun RequestAppPermissions() {
    val context = LocalContext.current
    val showAlarmDialog = remember { mutableStateOf(false) }

    // Launcher cho runtime permissions
    val runtimePermissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.forEach { (permission, granted) ->
            Log.d("ReminderPermissions", "$permission: $granted")
        }
    }

    // Khi Composable được gọi -> xin runtime permissions
    LaunchedEffect(Unit) {
        val neededPermissions = buildList {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                add(Manifest.permission.POST_NOTIFICATIONS)
            }
            // add các quyền khác bạn cần, ví dụ:
            // add(Manifest.permission.CAMERA)
        }
        if (neededPermissions.isNotEmpty()) {
            runtimePermissionsLauncher.launch(neededPermissions.toTypedArray())
        }

        // Check alarm permission (chỉ Android 12+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                showAlarmDialog.value = true
            }
        }
    }

    // Dialog xin quyền exact alarm
    if (showAlarmDialog.value) {
        AlertDialog(
            onDismissRequest = { showAlarmDialog.value = false },
            title = { Text("Yêu cầu quyền báo thức") },
            text = { Text("Ứng dụng cần quyền báo thức chính xác để nhắc nhở hoạt động đúng giờ. Bạn có muốn mở cài đặt để cấp quyền không?") },
            confirmButton = {
                TextButton(onClick = {
                    showAlarmDialog.value = false
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        context.startActivity(intent)
                    }
                }) {
                    Text("Đồng ý")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAlarmDialog.value = false }) {
                    Text("Hủy")
                }
            }
        )
    }
}
