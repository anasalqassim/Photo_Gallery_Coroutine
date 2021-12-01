package com.tuwaiq.photogallery.flickr.api

import com.tuwaiq.photogallery.flickr.models.FlickrResponse
import retrofit2.Call
import retrofit2.http.GET



interface FlickrApi {

    @GET("/services/rest/?method=flickr.interestingness.getList&" +
            "api_key=52b327b946b5f36725f7cae986520511&" +
            "extras=url_s&" +
            "format=json&nojsoncallback=1")
    fun fetchPhotos(): Call<FlickrResponse>

}