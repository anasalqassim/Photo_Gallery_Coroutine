package com.tuwaiq.photogallery.flickr.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.WorkRequest
import com.tuwaiq.photogallery.flickr.api.FlickrApi
import com.tuwaiq.photogallery.flickr.api.Interceptor
import com.tuwaiq.photogallery.flickr.models.FlickrResponse
import com.tuwaiq.photogallery.flickr.models.GalleryItem
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

private const val TAG = "FlickrRepo"
class FlickrRepo {

  private  val client = OkHttpClient.Builder()
        .addInterceptor(Interceptor())
        .build()


   private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.flickr.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

   private val flickrApi = retrofit.create(FlickrApi::class.java)

    fun fetchPhotosRequest():Call<FlickrResponse>{
        return flickrApi.fetchPhotos()
    }

    suspend fun fetchPhotos():List<GalleryItem> = fetchPhotosMetaData(flickrApi.fetchPhotos())


    fun searchPhotosRequest(query: String):Call<FlickrResponse>{
        return flickrApi.searchPhotos(query)
    }

    suspend fun searchPhotos(query: String):List<GalleryItem>{
        return fetchPhotosMetaData(flickrApi.searchPhotos(query))
    }

   private suspend fun fetchPhotosMetaData(flickrRequest: Call<FlickrResponse>)
    :List<GalleryItem>{
        var galleryItems:List<GalleryItem> = emptyList()

        val response:Response<FlickrResponse> = flickrRequest.awaitResponse()

        if (response.isSuccessful){
             galleryItems = response.body()?.photos?.galleryItems ?: emptyList()
            galleryItems = galleryItems.filterNot { it.url.isBlank() }


        }else{
            Log.e(TAG , "something gone wrong ${response.errorBody()}")
        }

        return galleryItems

    }

}