package com.hiutin.jetpackcomposetodo.ui.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.hiutin.jetpackcomposetodo.R
import com.hiutin.jetpackcomposetodo.common.consts.Consts

class ReminderReceiver : BroadcastReceiver() {
    companion object {
        const val TASK_REMINDER_CHANNEL_ID = "task_reminder_channel"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return

        val taskTitle = intent?.getStringExtra(Consts.EXTRA_TASK_TITLE)
            ?: context.getString(R.string.default_task_title)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create channel for Android O+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                TASK_REMINDER_CHANNEL_ID,
                context.getString(R.string.task_reminder_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, TASK_REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.default_task_title))
            .setContentText(taskTitle)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val notificationId = (intent?.getStringExtra(Consts.EXTRA_TASK_ID)?.hashCode() ?: 0)
        Log.d("ReminderReceiver", "Hello")

        notificationManager.notify(notificationId, notification)
    }
}

