package com.tuwaiq.photogallery

import android.content.Context
import androidx.preference.PreferenceManager

private const val QUERY_KEY = "query"
private const val LAST_RESULT_ID = "lastResult"

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

    fun setLastResultId(context: Context , lastResultId:String){
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(LAST_RESULT_ID,lastResultId)
            .apply()
    }

    fun getLastResultId(context: Context):String{
        val preferenceManager = PreferenceManager.getDefaultSharedPreferences(context)

        return preferenceManager.getString(LAST_RESULT_ID,"")!!
    }

}