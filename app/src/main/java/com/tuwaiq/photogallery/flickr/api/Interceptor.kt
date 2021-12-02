package com.tuwaiq.photogallery.flickr.api

import okhttp3.Interceptor
import okhttp3.Response


private const val API_KEY = "7c9fb950ff3dae155ea72620ee01c93e"
class Interceptor : Interceptor{



    override fun intercept(chain: Interceptor.Chain): Response {

        val currentRequest = chain.request()


        val newUrl = currentRequest.url().newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("format","json")
            .addQueryParameter("nojsoncallback","1")
            .addQueryParameter("extras","url_s")
            .addQueryParameter("safesearch","3")
            .build()


        val newRequest = currentRequest.newBuilder()
            .url(newUrl).build()

        return chain.proceed(newRequest)
    }
}