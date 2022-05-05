package com.semblanceoffunctionality.grocery.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.semblanceoffunctionality.grocery.GroceryFragment
import com.semblanceoffunctionality.grocery.ItemListFragment

const val MY_GROCERY_PAGE_INDEX = 0
const val ITEM_LIST_PAGE_INDEX = 1

class GroceryPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        MY_GROCERY_PAGE_INDEX to { GroceryFragment() },
        ITEM_LIST_PAGE_INDEX to { ItemListFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}
