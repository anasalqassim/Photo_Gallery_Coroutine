package com.tuwaiq.photogallery.flickr.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.WorkRequest
import com.tuwaiq.photogallery.flickr.api.FlickrApi
import com.tuwaiq.photogallery.flickr.models.FlickrResponse
import com.tuwaiq.photogallery.flickr.models.GalleryItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "FlickrRepo"
class FlickrRepo {


   private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.flickr.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

   private val flickrApi = retrofit.create(FlickrApi::class.java)

    suspend fun fetchPhotos():List<GalleryItem> = fetchPhotosMetaData(flickrApi.fetchPhotos())

   private suspend fun fetchPhotosMetaData(flickrRequest: Call<FlickrResponse>)
    :List<GalleryItem>{
        var galleryItems:List<GalleryItem> = emptyList()

        val response:Response<FlickrResponse> = flickrRequest.awaitResponse()

        if (response.isSuccessful){
             galleryItems = response.body()?.photos?.galleryItems ?: emptyList()
            galleryItems = galleryItems.filterNot { it.url.isBlank() }


        }else{
            Log.d(TAG , "something gone wrong ${response.errorBody()}")
        }

        return galleryItems

    }

}