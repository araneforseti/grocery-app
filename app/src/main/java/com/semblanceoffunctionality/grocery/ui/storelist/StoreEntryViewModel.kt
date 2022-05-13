package com.semblanceoffunctionality.grocery.ui.storelist

import com.semblanceoffunctionality.grocery.data.Store

class StoreEntryViewModel(val heldStore: Store) {
    val store
        get() = heldStore.name
}
