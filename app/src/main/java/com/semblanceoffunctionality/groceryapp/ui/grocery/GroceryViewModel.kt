package com.semblanceoffunctionality.groceryapp.ui.grocery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GroceryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is grocery Fragment"
    }
    val text: LiveData<String> = _text
}