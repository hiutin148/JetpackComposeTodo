package com.hiutin.jetpackcomposetodo.data.repositories

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.hiutin.jetpackcomposetodo.common.consts.Consts
import com.hiutin.jetpackcomposetodo.domain.models.Task
import com.hiutin.jetpackcomposetodo.domain.repositories.ReminderScheduler
import com.hiutin.jetpackcomposetodo.ui.receiver.ReminderReceiver
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class ReminderSchedulerImpl @Inject() constructor(val context: Context) : ReminderScheduler {
    override fun schedule(task: Task) {
        try {
            val date = task.date
            val time = task.time
            if (date == null || time == null) return

            val intent = Intent(context, ReminderReceiver::class.java).apply {
                putExtra(Consts.EXTRA_TASK_ID, task.id)
                putExtra(Consts.EXTRA_TASK_TITLE, task.title)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                task.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val localDateTime = LocalDateTime.of(date, time)
            val dueTime = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP, dueTime, pendingIntent
                    )
                } else {
                    val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(intent)
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, dueTime, pendingIntent
                )
            }
        } catch (e: Exception) {
            Log.e("ReminderSchedulerImpl", e.toString())
            throw e
        }
    }

    override fun cancel(task: Task) {
        TODO("Not yet implemented")
    }
}