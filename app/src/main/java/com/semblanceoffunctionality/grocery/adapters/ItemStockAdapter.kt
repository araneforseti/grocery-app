package com.semblanceoffunctionality.grocery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.semblanceoffunctionality.grocery.data.StockStatus
import com.semblanceoffunctionality.grocery.data.StockStatusEnum
import com.semblanceoffunctionality.grocery.databinding.ListStoreStatusBinding
import com.semblanceoffunctionality.grocery.ui.itemdetail.ItemDetailFragment
import com.semblanceoffunctionality.grocery.utilities.statusradio.StatusRadioGroup

/**
 * Adapter for the [RecyclerView] in [ItemDetailFragment].
 */
class ItemStockAdapter(
    private var groupListener: StatusRadioGroup.OnCheckedChangeListener
) : ListAdapter<StockStatus, RecyclerView.ViewHolder>(StockStatusDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StockStatusViewHolder(
            ListStoreStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            groupListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val status = getItem(position)

        (holder as StockStatusViewHolder).bind(status)
    }

    class StockStatusViewHolder(
        private val binding: ListStoreStatusBinding,
        groupListener: StatusRadioGroup.OnCheckedChangeListener
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.statusButtons.statusGroup.setOnCheckedChangeListener(groupListener)
        }

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
        return oldItem.item == newItem.item &&
                oldItem.store == newItem.store &&
                oldItem.stockStatus == newItem.stockStatus
    }
}
