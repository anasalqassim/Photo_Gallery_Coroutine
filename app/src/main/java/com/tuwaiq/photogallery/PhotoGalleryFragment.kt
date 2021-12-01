package com.tuwaiq.photogallery

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tuwaiq.photogallery.flickr.models.GalleryItem

class PhotoGalleryFragment : Fragment() {

    private lateinit var myRv:RecyclerView



    private  val viewModel: PhotoGalleryViewModel by lazy { ViewModelProvider(this)[PhotoGalleryViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.photo_gallery_fragment, container, false)
        myRv = v.findViewById(R.id.myRv)
        myRv.layoutManager = GridLayoutManager(context , 2)
    return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.photosLiveData().observe(
            viewLifecycleOwner, Observer {
                it?.let { galleryItems->
                    myRv.adapter = PhotosAdapter(galleryItems)
                }
            }
        )
    }

   private inner class PhotosHolder(val view: View):
       RecyclerView.ViewHolder(view){

           val photoItem:ImageView = itemView.findViewById(R.id.photo_item)

       fun bind(galleryItem: GalleryItem){
           photoItem.load(galleryItem.url)
       }

       }

   private inner class PhotosAdapter(val galleryItems:List<GalleryItem>):
       RecyclerView.Adapter<PhotosHolder>() {
       override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosHolder {
           val view = layoutInflater.inflate(
               R.layout.photo_list_item,
               parent,
               false
           )

           return PhotosHolder(view)
       }

       override fun onBindViewHolder(holder: PhotosHolder, position: Int) {
           val galleryItem = galleryItems[position]
           holder.bind(galleryItem)
       }

       override fun getItemCount(): Int = galleryItems.size
   }


}