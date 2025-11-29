package com.mohaberabi.inspekt.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import com.inspekt.notifiaction.R

class AndroidInspektNotificationManager(
    private val context: Context
) : InspektNotificationManager {
    companion object {
        const val CHANNEL_ID = "Inspekt"
    }


    private val notificationManager by lazy { requireNotNull(context.getSystemService<NotificationManager>()); }
    override fun showLocalNotification(
        title: String,
        body: String,
        id: Int,
        config: NotificationConfig
    ) {

        val builder = NotificationCompat.Builder(
            context,
            CHANNEL_ID
        )
        val notification = builder
            .setContentTitle(title)
            .setContentText(body)
            .setChannelId(CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(config.contentPendingIntent)
            .build()
        notificationManager.notify(id, notification)
    }

    fun initChannel() {
        if (Build.VERSION.SDK_INT >= 33) {
            runCatching {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_ID,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }
        }

    }
}