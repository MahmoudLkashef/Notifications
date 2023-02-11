package com.example.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class CounterNotificationService(private val context: Context) {

    companion object {
        const val COUNTER_CHANNEL_ID = "counter_channel"
        const val COUNTER_CHANNEL_NAME = "counter"
        const val IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT
        const val DESCRIPTION = "Used for increment counter notifications"
    }

    private val notificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(counter: Int) {
        val activityPendingIntent=PendingIntent.getActivity(
            context,
            1,
            Intent(context,MainActivity::class.java),
            if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val incrementPendingIntent=PendingIntent.getBroadcast(
            context,
            2,
            Intent(context,CounterNotificationReceiver::class.java),
            if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_add_card_24)
            .setContentTitle("Increment Counter")
            .setContentText("The counter is $counter")
            .setContentIntent(activityPendingIntent)
            .addAction(
                R.drawable.baseline_add_card_24,
                "Increment",
                incrementPendingIntent
            )
            .build()

        notificationManager.notify(1,notification)
    }
}