package com.tuwaiq.photogallery.broadcastsReceivers

import android.app.Activity
import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.tuwaiq.photogallery.workers.PollWorker

private const val TAG = "NotificationReceiver"
class NotificationReceiver:BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        Log.d(TAG , " action ${intent?.action}")

        if (resultCode != Activity.RESULT_OK){
            return
        }


        val notification:Notification = intent?.getParcelableExtra(PollWorker.NOTIFICATION_KEY)!!

        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(1 , notification)
    }
}