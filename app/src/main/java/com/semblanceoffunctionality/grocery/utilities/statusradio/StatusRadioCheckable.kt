package com.semblanceoffunctionality.grocery.utilities.statusradio

import android.view.View
import android.widget.Checkable

interface StatusRadioCheckable : Checkable {

    fun addOnCheckedChangeListener(listener: OnCheckedChangeListener)

    fun removeOnCheckedChangeListener(listener: OnCheckedChangeListener)

    fun interface OnCheckedChangeListener {
        fun onCheckedChange(radioButton: View, checked: Boolean)
    }
}
