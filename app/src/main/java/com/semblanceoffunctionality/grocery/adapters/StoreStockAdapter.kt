package com.semblanceoffunctionality.grocery.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.semblanceoffunctionality.grocery.data.StockStatus
import com.semblanceoffunctionality.grocery.databinding.ListItemStatusBinding
import com.semblanceoffunctionality.grocery.ui.itemlist.ItemListFragmentDirections
import com.semblanceoffunctionality.grocery.ui.storedetail.StoreDetailFragment
import com.semblanceoffunctionality.grocery.ui.storedetail.StoreDetailFragmentDirections
import com.semblanceoffunctionality.grocery.ui.storedetail.StoreDetailViewModel
import com.semblanceoffunctionality.grocery.utilities.statusradio.StatusButtonCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Adapter for the [RecyclerView] in [StoreDetailFragment].
 */
class StoreStockAdapter(
    private val storeDetailViewModel: StoreDetailViewModel
) : ListAdapter<StockStatus, RecyclerView.ViewHolder>(StoreStockStatusDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StockStatusViewHolder(
            ListItemStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            storeDetailViewModel
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val status = getItem(position)
        (holder as StockStatusViewHolder).bind(status)
    }

    class StockStatusViewHolder(
        private val binding: ListItemStatusBinding,
        private val storeDetailViewModel: StoreDetailViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        init {}

        fun bind(listStatus: StockStatus) {
            binding.apply {
                stockStatus = listStatus
                clickListener = View.OnClickListener {
                    val action = StoreDetailFragmentDirections.actionNavStoreDetailToNavItemDetail(listStatus.item)
                    it.findNavController().navigate(action)
                }
                binding.statusButtons.stockedCallback = StatusButtonCallback.SetStockedCallback { item, store ->
                    if (store != null && item != null) {
                        storeDetailViewModel.setStockedStatus(item)
                        CoroutineScope(Dispatchers.IO).launch {
                            stockStatus = storeDetailViewModel.getStatusForItem(item)
                        }
                    }
                }
                binding.statusButtons.unknownCallback = StatusButtonCallback.SetUnknownCallback { item, store ->
                    if (store != null && item != null) {
                        storeDetailViewModel.setStockedUnknownStatus(item)
                        CoroutineScope(Dispatchers.IO).launch {
                            stockStatus = storeDetailViewModel.getStatusForItem(item)
                        }
                    }
                }
                binding.statusButtons.notStockedCallback = StatusButtonCallback.SetNotStockedCallback { item, store ->
                    if (store != null && item != null) {
                        storeDetailViewModel.setNotStockedStatus(item)
                        CoroutineScope(Dispatchers.IO).launch {
                            stockStatus = storeDetailViewModel.getStatusForItem(item)
                        }
                    }
                }
                executePendingBindings()
            }
        }
    }
}

private class StoreStockStatusDiffCallback : DiffUtil.ItemCallback<StockStatus>() {

    override fun areItemsTheSame(oldItem: StockStatus, newItem: StockStatus): Boolean {
        return oldItem.item == newItem.item && oldItem.store == newItem.store
    }

    override fun areContentsTheSame(oldItem: StockStatus, newItem: StockStatus): Boolean {
        return oldItem == newItem
    }
}
