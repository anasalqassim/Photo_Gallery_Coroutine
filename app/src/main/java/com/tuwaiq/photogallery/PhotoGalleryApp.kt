package com.tuwaiq.photogallery

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

const val NOTIFICATION_ID = "id"
class PhotoGalleryApp :Application() {

    override fun onCreate() {
        super.onCreate()

        val notificationChannelName = resources.getString(R.string.notificationChannelName)


        val notificationImportance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(NOTIFICATION_ID,
        notificationChannelName,
        notificationImportance)

        val notificationManager = getSystemService(NotificationManager::class.java)

        notificationManager.createNotificationChannel(channel)
    }
}