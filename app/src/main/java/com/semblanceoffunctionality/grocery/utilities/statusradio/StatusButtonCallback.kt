package com.semblanceoffunctionality.grocery.utilities.statusradio

class StatusButtonCallback {
    fun interface SetStockedCallback {
        fun setStocked(item: String?, store: String?)
    }

    fun interface SetUnknownCallback {
        fun setUnknown(item: String?, store: String?)
    }

    fun interface SetNotStockedCallback {
        fun setNotStocked(item: String?, store: String?)
    }
}