package com.semblanceoffunctionality.grocery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.semblanceoffunctionality.grocery.data.StockStatus
import com.semblanceoffunctionality.grocery.data.StockStatusEnum
import com.semblanceoffunctionality.grocery.databinding.ListStoreStatusBinding

/**
 * Adapter for the [RecyclerView] in [ItemDetailFragment].
 */
class ItemStockAdapter : ListAdapter<StockStatus, RecyclerView.ViewHolder>(StockStatusDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StockStatusViewHolder(
            ListStoreStatusBinding.inflate(
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
        private val binding: ListStoreStatusBinding
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

private class StockStatusDiffCallback : DiffUtil.ItemCallback<StockStatus>() {

    override fun areItemsTheSame(oldItem: StockStatus, newItem: StockStatus): Boolean {
        return oldItem.item == newItem.item && oldItem.store == newItem.store
    }

    override fun areContentsTheSame(oldItem: StockStatus, newItem: StockStatus): Boolean {
        return oldItem == newItem
    }
}
