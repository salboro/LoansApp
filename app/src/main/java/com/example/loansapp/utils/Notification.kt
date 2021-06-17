package com.example.loansapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.example.loansapp.R
import com.example.loansapp.ui.MainActivity

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(messageBody: String, context: Context) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            context.getString(R.string.notification_channel_id),
            context.getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_LOW
        )
        notificationChannel.apply {
            enableLights(true)
            lightColor = Color.WHITE
            enableVibration(true)
            description = "Stopwatch"
        }
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }

    val notificationIntent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context,
        NOTIFICATION_ID,
        notificationIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val notificationBuilder = NotificationCompat.Builder(
        context,
        context.getString(R.string.notification_channel_id)
    ).apply {
        setSmallIcon(R.drawable.ic_logo)
        setContentTitle(context.getString(R.string.notification_title))
        setContentText(messageBody)
        setContentIntent(pendingIntent)
        setAutoCancel(true)
    }

    notify(NOTIFICATION_ID, notificationBuilder.build())
}