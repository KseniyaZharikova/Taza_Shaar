package com.tazashaar.kseniya.zerowaste.adapters

import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tazashaar.kseniya.zerowaste.R
import com.tazashaar.kseniya.zerowaste.data.ReceptionPoint
import com.tazashaar.kseniya.zerowaste.utils.CallAlertDialog


class PointDetailsAdapter(private val myDataset: ReceptionPoint, private val imagesCollection: List<String>) : RecyclerView.Adapter<PointDetailsAdapter.MyViewHolder>(), ViewPager.OnPageChangeListener {

    var filterItems: ReceptionPoint? = null
    private var context: Context? = null
    private var imageCount: Int? = 0
    private lateinit var imageViewLeft: ImageView
    private lateinit var imageViewRight: ImageView

    init {
        filterItems = myDataset
        imageCount = imagesCollection.size
    }


    private fun isImageHasOne() {
        if (imagesCollection.size <= 1) {
            imageViewLeft.visibility = View.GONE
            imageViewRight.visibility = View.GONE
        }
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
    override fun onPageScrollStateChanged(p0: Int) {}

    override fun onPageSelected(p0: Int) {
        when {
            imagesCollection.size == 1 -> {
                imageViewLeft.visibility = View.GONE
                imageViewRight.visibility = View.GONE
            }
            p0 == 0 -> {
                imageViewLeft.visibility = View.GONE
                imageViewRight.visibility = View.VISIBLE
            }
            p0 == imagesCollection.size - 1 -> {
                imageViewLeft.visibility = View.VISIBLE
                imageViewRight.visibility = View.GONE
            }
            else -> {
                imageViewLeft.visibility = View.VISIBLE
                imageViewRight.visibility = View.VISIBLE
            }
        }




    }

    class MyViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        val tvName = v.findViewById<TextView>(R.id.tvName)
        val tvAddress = v.findViewById<TextView>(R.id.tvAddress)
        val tvPhone = v.findViewById<TextView>(R.id.tvPhone)
        val tvWorkTime = v.findViewById<TextView>(R.id.tvWorkTime)
        val tvPrice = v.findViewById<TextView>(R.id.tvPrice)
        val viewPager = v.findViewById<ViewPager>(R.id.viewPager)
        val tvInfo: TextView = v.findViewById(R.id.tvDesc)
        val imgPrev: ImageView = v.findViewById(R.id.imgPrev)
        val imgNext: ImageView = v.findViewById(R.id.imgNext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointDetailsAdapter.MyViewHolder {
        context = parent.context
        return MyViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_points_info_details, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        imageViewRight = holder.imgPrev
        imageViewLeft = holder.imgNext
        holder.tvName.visibility = View.GONE
        holder.tvAddress.text = filterItems!!.address
        holder.tvPhone.text = filterItems!!.phone
        holder.tvWorkTime.text = filterItems!!.work_time
        holder.tvPrice.text = filterItems!!.price
        holder.viewPager.adapter = ImageAdapter(context, imagesCollection)
        holder.tvInfo.text = filterItems!!.description
        holder.viewPager.currentItem
        holder.viewPager.setOnPageChangeListener(this)
        holder.imgNext.setOnClickListener { nextImage(holder.viewPager, holder) }
        holder.imgPrev.setOnClickListener { prevImage(holder.viewPager, holder) }
        if (imagesCollection.isEmpty()) {
            holder.viewPager.visibility = View.GONE
            holder.imgNext.visibility = View.GONE
            holder.imgPrev.visibility = View.GONE
        }

        holder.tvPhone.setOnClickListener { CallAlertDialog.getCall(context!!, filterItems!!.phone) }
        isImageHasOne()
    }

    private fun call() {}

    private fun prevImage(viewPager: ViewPager, holder: MyViewHolder) {
        val currentIndex = viewPager.currentItem
        if (currentIndex < imagesCollection.size) {
            viewPager.currentItem = currentIndex + 1
        }
    }

    private fun nextImage(viewPager: ViewPager, holder: MyViewHolder) {
        val currentIndex = viewPager.currentItem
        if (currentIndex > 0) {
            viewPager.currentItem = currentIndex - 1
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

}

