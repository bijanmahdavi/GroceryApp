package com.example.groceryappdemo.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.groceryappdemo.Fragments.FirstSubCategoryFragment

class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            //0 -> MobileFragment()
            //1 -> LaptopFragment()
            else -> FirstSubCategoryFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }
}