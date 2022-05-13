package com.semblanceoffunctionality.grocery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.semblanceoffunctionality.grocery.data.StockStatus
import com.semblanceoffunctionality.grocery.databinding.ListItemStatusBinding
import com.semblanceoffunctionality.grocery.ui.storedetail.StoreDetailFragment

/**
 * Adapter for the [RecyclerView] in [StoreDetailFragment].
 */
class StoreStockAdapter : ListAdapter<StockStatus, RecyclerView.ViewHolder>(StoreStockStatusDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StockStatusViewHolder(
            ListItemStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val status = getItem(position)
        (holder as StockStatusViewHolder).bind(status)
    }

    class StockStatusViewHolder(
        private val binding: ListItemStatusBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {}

        fun bind(listStatus: StockStatus) {
            binding.apply {
                stockStatus = listStatus
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
