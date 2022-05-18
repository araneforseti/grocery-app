package com.semblanceoffunctionality.grocery.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.semblanceoffunctionality.grocery.R
import com.semblanceoffunctionality.grocery.data.StockStatus
import com.semblanceoffunctionality.grocery.data.StockStatusEnum
import com.semblanceoffunctionality.grocery.data.StockStatusRepository
import com.semblanceoffunctionality.grocery.databinding.ListStoreStatusBinding
import com.semblanceoffunctionality.grocery.generated.callback.OnClickListener
import com.semblanceoffunctionality.grocery.ui.itemdetail.ItemDetailFragment
import com.semblanceoffunctionality.grocery.ui.itemdetail.ItemDetailViewModel
import com.semblanceoffunctionality.grocery.utilities.statusradio.StatusButtonCallback
import com.semblanceoffunctionality.grocery.utilities.statusradio.StatusRadioButton
import com.semblanceoffunctionality.grocery.utilities.statusradio.StatusRadioGroup

/**
 * Adapter for the [RecyclerView] in [ItemDetailFragment].
 */
class ItemStockAdapter(
    private val itemDetailViewModel: ItemDetailViewModel
) : ListAdapter<StockStatus, RecyclerView.ViewHolder>(StockStatusDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StockStatusViewHolder(
            ListStoreStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemDetailViewModel
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val status = getItem(position)

        (holder as StockStatusViewHolder).bind(status)
    }

    class StockStatusViewHolder(
        private val binding: ListStoreStatusBinding,
        private val itemDetailViewModel: ItemDetailViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        init {}

        fun bind(listStatus: StockStatus) {
            binding.apply {
                stockStatus = listStatus
                binding.statusButtons.stockedCallback = StatusButtonCallback.SetStockedCallback { item, store ->
                    if (store != null && item != null) {
                        itemDetailViewModel.setStockedStatus(store)
                        this.notifyChange()
                    }
                }
                binding.statusButtons.unknownCallback = StatusButtonCallback.SetUnknownCallback { item, store ->
                    if (store != null && item != null) {
                        itemDetailViewModel.setStockedUnknownStatus(store)
                    }
                    this.notifyChange()
                }
                binding.statusButtons.notStockedCallback = StatusButtonCallback.SetNotStockedCallback { item, store ->
                    if (store != null && item != null) {
                        itemDetailViewModel.setNotStockedStatus(store)
                    }
                    this.notifyChange()
                }
                executePendingBindings()
            }
        }
    }
}

private class StockStatusDiffCallback : DiffUtil.ItemCallback<StockStatus>() {

    override fun areItemsTheSame(oldItem: StockStatus, newItem: StockStatus): Boolean {
        return oldItem.item == newItem.item && oldItem.store == newItem.store
    }

    override fun areContentsTheSame(oldItem: StockStatus, newItem: StockStatus): Boolean {
        return oldItem.item == newItem.item &&
                oldItem.store == newItem.store &&
                oldItem.stockStatus == newItem.stockStatus
    }
}
