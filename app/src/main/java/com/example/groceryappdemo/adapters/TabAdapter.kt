package com.example.groceryappdemo.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.groceryappdemo.fragments.SubCategoryFragment
import com.example.groceryappdemo.models.SubCategoryDataItem

class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var mFragmentList: ArrayList<Fragment> = ArrayList()
    var mTitleList: ArrayList<String> = ArrayList()

    override fun getCount(): Int {
        return mTitleList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList[position]
    }

    fun addFragment(subCategory: SubCategoryDataItem) {
        mTitleList.add(subCategory.subName)
        mFragmentList.add(SubCategoryFragment.newInstance(subCategory.subId))
    }
}


