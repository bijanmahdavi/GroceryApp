package com.example.groceryappdemo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappdemo.adapters.SubProductAdapter
import com.example.groceryappdemo.models.ProductData
import com.example.groceryappdemo.models.SubCategoryDataItem
import com.example.groceryappdemo.R
import com.example.groceryappdemo.app.Endpoints
import kotlinx.android.synthetic.main.fragment_sub_category.view.*
import org.json.JSONObject

class SubCategoryFragment : Fragment() {
    private var subId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subId = it.getInt(SubCategoryDataItem.KEY_SUB_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_sub_category, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        val mList = ArrayList<ProductData>()
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getProductBySubId(subId),
            {
                val obj = JSONObject(it)
                val data = obj.getJSONArray("data")
                for(i in 0 until data.length()) {
                    val data = data.getJSONObject(i)
                    val item = ProductData(data.getInt("__v"),
                        data.getString("_id"),
                        data.getInt("catId"),
                        data.getString("created"),
                        data.getString("description"),
                        data.getString("image"),
                        data.getInt("mrp"),
                        data.getInt("position"),
                        data.getInt("price"),
                        data.getString("productName"),
                        data.getInt("quantity"),
                        data.getBoolean("status"),
                        data.getInt("subId"),
                        data.getString("unit")
                    )
                    mList.add(item)
                }
                val adapter = SubProductAdapter(view.context, mList)
                view.sub_category_list_view.adapter = adapter
                view.sub_category_list_view.layoutManager = LinearLayoutManager(activity)
            },
            {

            }
        )
        Volley.newRequestQueue(activity).add(request)
    }

    companion object {

        @JvmStatic
        fun newInstance(subId: Int) =
            SubCategoryFragment().apply {
                arguments = Bundle().apply {
                    putInt(SubCategoryDataItem.KEY_SUB_ID, subId)
                }
            }
    }
}