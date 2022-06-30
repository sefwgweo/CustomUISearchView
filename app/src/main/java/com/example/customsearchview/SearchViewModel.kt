package com.example.customsearchview

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    val searchData: MutableLiveData<String> = MutableLiveData()

    val backgroundColor = Color.BLACK
    val textColor = Color.WHITE
    val hintTextColor = Color.RED
    val searchBoxBackgroundColor = Color.GREEN
    val searchBoxBackgroundAlpha = 100
    val clearColor = Color.TRANSPARENT

    fun search(query: String) {
        searchData.postValue(query)
    }
}