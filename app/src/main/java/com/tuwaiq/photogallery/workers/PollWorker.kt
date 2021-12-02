package com.tuwaiq.photogallery.workers

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.tuwaiq.photogallery.NOTIFICATION_ID
import com.tuwaiq.photogallery.PhotoGalleryShearedPreference
import com.tuwaiq.photogallery.PhotosGalleryActivity
import com.tuwaiq.photogallery.R
import com.tuwaiq.photogallery.flickr.models.GalleryItem
import com.tuwaiq.photogallery.flickr.repo.FlickrRepo

private const val TAG = "PollWorker"
class PollWorker(private val context: Context,workerParams: WorkerParameters)  :
    Worker(context , workerParams) {

    override fun doWork(): Result {

        Log.e(TAG , "from the worker")

        val query = PhotoGalleryShearedPreference.getQuery(context)
        val lasResultId = PhotoGalleryShearedPreference.getLastResultId(context)

        val galleryItems:List<GalleryItem> = if (query.isBlank()){
            FlickrRepo()
                .fetchPhotosRequest()
                .execute()
                .body()
                ?.photos
                ?.galleryItems
        }else{
            FlickrRepo()
                .searchPhotosRequest(query)
                .execute()
                .body()
                ?.photos
                ?.galleryItems

        } ?: emptyList()

        if (galleryItems.isEmpty()){
            Log.d(TAG , "empty list")

            return Result.success()
        }
        val resultId = galleryItems.first().id
        if (resultId == lasResultId){
            Log.d(TAG , "GOT OLD PICS")

        }else{
            Log.d(TAG , "GOT new PICS")
            PhotoGalleryShearedPreference.setLastResultId(context,resultId)

            val intent = PhotosGalleryActivity.newIntent(context)
            val pendingIntent = PendingIntent.getActivity(
                context,0,
                intent,PendingIntent.FLAG_IMMUTABLE
            )


          val  res = context.resources
            val notification = Notification.Builder(
                context, NOTIFICATION_ID)
                .setContentTitle(res.getString(R.string.notificationTitle))
                .setContentIntent(pendingIntent)
                .setContentText(res.getString(R.string.notificationText))
                .setSmallIcon(R.drawable.ic_baseline_add_photo_alternate_24)
                .setAutoCancel(true)
                .build()



            showBackgroundNotification(notification)


        }

        return Result.success()
    }

    private fun showBackgroundNotification(notification: Notification){

        val intent = Intent(NOTIFICATION_ACTION).apply {
            putExtra(NOTIFICATION_KEY , notification)
        }


        context.sendOrderedBroadcast(intent , NOTIFICATION_PERM)


    }

    companion object{
       const val  NOTIFICATION_ACTION = "com.tuwaiq.photosgallery.SHOW_NOTIFICATION"
       const val  NOTIFICATION_PERM = "com.tuwaiq.photogallery.PRIVATE"
        const val NOTIFICATION_KEY = "NOTIFICATION_KEY"
    }

}