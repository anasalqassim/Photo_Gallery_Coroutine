package com.tuwaiq.photogallery

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PhotosGalleryActivity : AppCompatActivity() {

    companion object{

        fun newIntent(context: Context):Intent{
            return Intent(context , PhotosGalleryActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos_gallery)


    }

}