package com.semblanceoffunctionality.grocery.ui.itemdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.semblanceoffunctionality.grocery.data.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel used in [ItemDetailFragment].
 */
@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val itemRepository: ItemRepository
) : ViewModel() {

    val itemId: String = savedStateHandle.get<String>(ITEM_ID_SAVED_STATE_KEY)!!
    val item = itemRepository.getItem(itemId).asLiveData()

    fun addItemToGrocery() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.e("info", "Item: " + itemId + " should be added to list")
            itemRepository.setWanted(itemId)
        }
    }

    fun removeItemFromGrocery() {
        CoroutineScope(Dispatchers.IO).launch {
            itemRepository.removeWanted(itemId)
        }
    }

    fun deleteItem() {
        CoroutineScope(Dispatchers.IO).launch {
            itemRepository.deleteItem(itemId)
        }
    }

    companion object {
        private const val ITEM_ID_SAVED_STATE_KEY = "itemId"
    }
}
