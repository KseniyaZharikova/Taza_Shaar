package com.kseniya.tazar.adapters

import android.content.Context
import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kseniya.tazar.R
import com.kseniya.tazar.ui.activities.FullScreenImageActivity
import java.util.ArrayList





class ImageAdapter(private val context: Context?, private val images: List<String>) : PagerAdapter(), View.OnClickListener {
    override fun onClick(v: View?) {
        val intent = Intent(context, FullScreenImageActivity::class.java)
        val usersArrayList = ArrayList<String>(images)
        intent.putStringArrayListExtra("images", usersArrayList)
        intent.putExtra("position", v!!.tag as Int)
        context!!.startActivity(intent)
    }

    private var mLayoutInflater: LayoutInflater? = null

    init {
        mLayoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
    }

    override fun isViewFromObject(view: View, p1: Any): Boolean {
        return view === p1 as LinearLayout
    }

    override fun getCount(): Int {
        //if (images.isEmpty())
          //  return 1
        return images.size
    }

    private fun getLink(position: Int): String {
        return if (images.isEmpty()) {
            ""
        } else {
            images[position].trim()
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = mLayoutInflater!!.inflate(R.layout.pager_item, container, false)
        view.setOnClickListener(this)
        val imageView = view.findViewById<ImageView>(R.id.imageViewPoints)
        view.tag = position
        Glide.with(context!!)
                .load(getLink(position))

                .apply(RequestOptions().placeholder(R.drawable.image_placeholder).error(R.drawable.image_placeholder))
                .into(imageView)
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}