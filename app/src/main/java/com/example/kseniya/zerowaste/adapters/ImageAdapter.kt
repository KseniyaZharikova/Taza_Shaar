package com.example.kseniya.zerowaste.adapters

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.kseniya.zerowaste.R


class ImageAdapter(private val context: Context?, private val images: List<String>) : PagerAdapter() {
    private var mLayoutInflater: LayoutInflater? = null

    init {
        mLayoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
    }

    override fun isViewFromObject(view: View, p1: Any): Boolean {
        return view === p1 as LinearLayout
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = mLayoutInflater!!.inflate(R.layout.pager_item, container, false)
        val imageView = view.findViewById<ImageView>(R.id.imageViewPoints)
        Glide.with(context!!)
                .load(images[position])
                .into(imageView)
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}