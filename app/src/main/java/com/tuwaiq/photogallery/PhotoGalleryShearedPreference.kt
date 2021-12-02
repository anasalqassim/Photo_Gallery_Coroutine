package com.tuwaiq.photogallery

import android.content.Context
import androidx.preference.PreferenceManager

private const val QUERY_KEY = "query"

object PhotoGalleryShearedPreference {

    fun setQuery(context: Context , query: String){
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(QUERY_KEY , query)
            .apply()
    }
    fun getQuery(context: Context):String{
        val preferenceManager = PreferenceManager.getDefaultSharedPreferences(context)

        return preferenceManager.getString(QUERY_KEY,"")!!
    }

}