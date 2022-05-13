package com.semblanceoffunctionality.grocery.ui.storedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import com.semblanceoffunctionality.grocery.R
import com.semblanceoffunctionality.grocery.adapters.StoreStockAdapter
import com.semblanceoffunctionality.grocery.data.Store
import com.semblanceoffunctionality.grocery.databinding.FragmentStoreDetailBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a single Store detail screen.
 */
@AndroidEntryPoint
class StoreDetailFragment : Fragment() {

    private val storeDetailViewModel: StoreDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = DataBindingUtil.inflate<FragmentStoreDetailBinding>(
            inflater,
            R.layout.fragment_store_detail,
            container,
            false
        ).apply {
            viewModel = storeDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            deleteCallback = DeleteCallback { store ->
                store?.let {
                    storeDetailViewModel.deleteStore()
                    Snackbar.make(root, R.string.deleted_store, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
        setHasOptionsMenu(true)

        val adapter = StoreStockAdapter()
        binding.stockItemList.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: StoreStockAdapter) {
        storeDetailViewModel.statuses.observe(viewLifecycleOwner) { statuses ->
            adapter.submitList(statuses)
        }
    }

    fun interface DeleteCallback {
        fun delete(store: Store?)
    }
}
