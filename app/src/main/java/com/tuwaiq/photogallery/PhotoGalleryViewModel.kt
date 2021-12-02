package com.tuwaiq.photogallery

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.tuwaiq.photogallery.flickr.models.GalleryItem
import com.tuwaiq.photogallery.flickr.repo.FlickrRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoGalleryViewModel(private val context: Application) : AndroidViewModel(context) {

    private val repo = FlickrRepo()

    private val searchTermLiveData:MutableLiveData<String> = MutableLiveData()

    private val searchTerm:String
        get() = PhotoGalleryShearedPreference.getQuery(context)

    init {
        searchTermLiveData.value = searchTerm

    }

    fun photosLiveData():LiveData<List<GalleryItem>> {
        var tempList:List<GalleryItem> = emptyList()
        val tempLiveData:MutableLiveData<List<GalleryItem>> = MutableLiveData()



     return Transformations.switchMap(searchTermLiveData) {term->


             viewModelScope.launch(Dispatchers.IO) {

                 tempList = if (term.isBlank()){
                     repo.fetchPhotos()
                 }else{
                     repo.searchPhotos(term)
                 }


             }.invokeOnCompletion {

                 viewModelScope.launch {

                     tempLiveData.value = tempList

                 }
             }

             tempLiveData



     }


    }

    fun setSearchTerm(query: String){
        PhotoGalleryShearedPreference.setQuery( context , query)
        searchTermLiveData.value = query

    }

}