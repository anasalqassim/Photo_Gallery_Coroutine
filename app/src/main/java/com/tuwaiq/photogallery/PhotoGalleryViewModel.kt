package com.tuwaiq.photogallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuwaiq.photogallery.flickr.models.GalleryItem
import com.tuwaiq.photogallery.flickr.repo.FlickrRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoGalleryViewModel : ViewModel() {

    private val repo = FlickrRepo()

    fun photosLiveData():LiveData<List<GalleryItem>> {
        val photosLiveData:MutableLiveData<List<GalleryItem>> = MutableLiveData()

        var tempList:List<GalleryItem> = emptyList()
       viewModelScope.launch(Dispatchers.IO) {
          tempList = repo.fetchPhotos()
       }.invokeOnCompletion {
           viewModelScope.launch {
               photosLiveData.value = tempList
           }
       }

        return photosLiveData
    }

}