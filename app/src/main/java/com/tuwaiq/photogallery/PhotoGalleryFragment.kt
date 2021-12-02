package com.tuwaiq.photogallery

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.SearchView

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tuwaiq.photogallery.flickr.models.GalleryItem

class PhotoGalleryFragment : Fragment() {

    private lateinit var myRv:RecyclerView



    private  val viewModel: PhotoGalleryViewModel by lazy { ViewModelProvider(this)[PhotoGalleryViewModel::class.java] }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu,menu)

        val searchItem = menu.findItem(R.id.search_item)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {

            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        viewModel.setSearchTerm(query)
                    }

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })


        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.clear_search -> {viewModel.setSearchTerm("")
                true
            }
           else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
            viewLifecycleOwner,  {
                it?.let { galleryItems->
                    myRv.adapter = PhotosAdapter(galleryItems)
                }
            }
        )
    }

   private inner class PhotosHolder(view: View):
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