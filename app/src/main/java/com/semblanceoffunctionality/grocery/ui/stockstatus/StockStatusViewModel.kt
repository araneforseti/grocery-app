package com.semblanceoffunctionality.grocery.ui.stockstatus

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.semblanceoffunctionality.grocery.data.StockStatus
import com.semblanceoffunctionality.grocery.data.StockStatusEnum
import com.semblanceoffunctionality.grocery.data.StockStatusRepository
import com.semblanceoffunctionality.grocery.data.StoreRepository
import com.semblanceoffunctionality.grocery.ui.storedetail.StoreDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * The ViewModel used in [StatusButtons].
 */
@HiltViewModel
class StockStatusViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val stockStatusRepository: StockStatusRepository
) : ViewModel() {
    val stockStatus: StockStatus = savedStateHandle.get<StockStatus>(STOCK_SAVED_STATE_KEY)!!
    val store = stockStatus.store
    val item = stockStatus.item

    fun isStocked(): Boolean {
        return stockStatus.stockStatus == StockStatusEnum.STOCKED
    }

    fun isUnknown(): Boolean {
        return stockStatus.stockStatus == StockStatusEnum.UNKNOWN
    }

    fun isNotStocked(): Boolean {
        return stockStatus.stockStatus == StockStatusEnum.NOT_STOCKED
    }

    fun setStockedStatus() {
        stockStatusRepository.setStockedStatus(stockStatus.store, stockStatus.item)
    }

    fun setUnknownStatus() {
        stockStatusRepository.setUnknownStatus(stockStatus.store, stockStatus.item)
    }

    fun setNotStockedStatus() {
        stockStatusRepository.setNotStockedStatus(stockStatus.store, stockStatus.item)
    }
//
//    override fun equals(other: StockStatusViewModel?): Boolean {
//        return if (other != null) {
//            stockStatus.store == other.stockStatus.store &&
//                    stockStatus.item == other.stockStatus.item &&
//                    stockStatus.stockStatus == other.stockStatus.stockStatus
//        } else run {
//            false
//        }
//    }

    companion object {
        private const val STOCK_SAVED_STATE_KEY = "store"
    }
}