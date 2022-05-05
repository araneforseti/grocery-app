package com.semblanceoffunctionality.grocery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.semblanceoffunctionality.grocery.adapters.MY_GROCERY_PAGE_INDEX
import com.semblanceoffunctionality.grocery.adapters.ITEM_LIST_PAGE_INDEX
import com.semblanceoffunctionality.grocery.adapters.GroceryPagerAdapter
import com.semblanceoffunctionality.grocery.databinding.FragmentViewPagerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = GroceryPagerAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            MY_GROCERY_PAGE_INDEX -> R.drawable.grocery_tab_selector
            ITEM_LIST_PAGE_INDEX -> R.drawable.item_list_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_GROCERY_PAGE_INDEX -> getString(R.string.my_grocery_title)
            ITEM_LIST_PAGE_INDEX -> getString(R.string.item_list_title)
            else -> null
        }
    }
}
