package com.example.groceryappdemo.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryappdemo.Adapters.TabAdapter
import com.example.groceryappdemo.R
import kotlinx.android.synthetic.main.activity_sub_category.*

class SubCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        init()
    }

    private fun init() {

        var myAdapter = TabAdapter(supportFragmentManager)
        view_pager.adapter = myAdapter
        tab_layout.setupWithViewPager(view_pager) // tab nav bar on top

        tab_layout.getTabAt(0)?.setIcon(R.drawable.ic_baseline_looks_one_24)
        tab_layout.getTabAt(1)?.setIcon(R.drawable.ic_baseline_looks_two_24)
        tab_layout.getTabAt(2)?.setIcon(R.drawable.ic_baseline_looks_3_24)
    }
}