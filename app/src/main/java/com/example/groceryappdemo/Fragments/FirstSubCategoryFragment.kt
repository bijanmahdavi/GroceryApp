package com.example.groceryappdemo.Fragments

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryappdemo.Adapters.CategoryAdapter
import com.example.groceryappdemo.Adapters.SubCategoryAdapter
import com.example.groceryappdemo.Models.CategoryDataItem
import com.example.groceryappdemo.Models.ProductData
import com.example.groceryappdemo.R
import kotlinx.android.synthetic.main.category_grid_layout.view.*
import kotlinx.android.synthetic.main.fragment_first_sub_category.view.*

class FirstSubCategoryFragment : Fragment() {

    private var mList: ArrayList<ProductData> = ArrayList()
    var listener: OnFragmentInteraction? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_first_sub_category, container, false)
        init(view)
        return view
    }


    private fun init(view: View) {
        view.first_sub_category_list_view.layoutManager = LinearLayoutManager(view.context)
        var listViewAdapter = SubCategoryAdapter(view.context, mList)
        view.first_sub_category_list_view.adapter = listViewAdapter
        view.setOnClickListener {
            var src: ImageView? = view.grid_img_src
            listener?.onImageClicked(src)
        }
        generateData()
    }

    interface OnFragmentInteraction {
        fun onImageClicked(name: ImageView?)
    }

    fun setOnFragmentInteractionListener(onFragmentInteraction: OnFragmentInteraction) {
        listener = onFragmentInteraction
    }

    private fun generateData() {
        mList.add(ProductData(1,",",3,"","Some fresh salad","category_veg.jpg",3,1, 0, "Vegis", 1, true, 1, ""))
        mList.add(ProductData(1,",",3,"","Some yummy Chicken","category_chicken.jpg",3,1, 0, "Chicken", 1, true, 1, ""))

    }
}