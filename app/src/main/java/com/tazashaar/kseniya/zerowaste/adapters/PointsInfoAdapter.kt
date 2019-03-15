package com.tazashaar.kseniya.zerowaste.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tazashaar.kseniya.zerowaste.R
import com.tazashaar.kseniya.zerowaste.data.ReceptionPoint
import com.tazashaar.kseniya.zerowaste.interfaces.SortedList
import com.tazashaar.kseniya.zerowaste.utils.CallAlertDialog

class PointsInfoAdapter(private val myDataset: List<ReceptionPoint>, private val viewInterface: SortedList) : RecyclerView.Adapter<PointsInfoAdapter.MyViewHolder>() {

    var filterItems: List<ReceptionPoint> = ArrayList()
    private lateinit var context: Context

    init {
        filterItems = myDataset
    }

    class MyViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        val tvName = v.findViewById<TextView>(R.id.tvName)
        val tvAddress = v.findViewById<TextView>(R.id.tvAddress)
        val tvPhone = v.findViewById<TextView>(R.id.tvPhone)
        val tvWorkTime = v.findViewById<TextView>(R.id.tvWorkTime)
        val tvPrice = v.findViewById<TextView>(R.id.tvPrice)
        val tvInfo = v.findViewById<TextView>(R.id.tvDesc)
        val imgInfo = v.findViewById<ImageView>(R.id.desc_image)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        return MyViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_point_info, parent, false))
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = filterItems[position]
        holder.tvName.text = item.name
        holder.tvAddress.text = item.address
        holder.tvPhone.text = item.phone
        holder.tvWorkTime.text = item.work_time
        holder.tvPrice.text = item.price
        holder.itemView.setOnClickListener {
            viewInterface.onClickItem(position)
        }
        holder.tvPhone.setOnClickListener { CallAlertDialog.getCall(context, item.phone) }

        holder.imgInfo.visibility = View.GONE
        holder.tvInfo.visibility = View.GONE

    }


    override fun getItemCount(): Int {
        return filterItems.size
    }

}

