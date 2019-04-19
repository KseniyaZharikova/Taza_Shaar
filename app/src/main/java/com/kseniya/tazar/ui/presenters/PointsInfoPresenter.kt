package com.kseniya.tazar.ui.presenters

import android.support.v7.widget.RecyclerView
import com.kseniya.tazar.adapters.PointsInfoAdapter
import com.kseniya.tazar.data.ReceptionPoint
import com.kseniya.tazar.interfaces.SortedList

class PointsInfoPresenter {
    private lateinit var viewInterface: SortedList

    private var adapter: PointsInfoAdapter? = null
    private lateinit var items: List<ReceptionPoint>

    fun bind(viewInterface: SortedList,items: List<ReceptionPoint>) {
        this.items = items
        this.viewInterface = viewInterface
    }

    fun bindRecyclerView(recyclerView: RecyclerView) {
        adapter = PointsInfoAdapter( items, viewInterface)
        recyclerView.adapter = adapter

    }



    fun pointsForPosition(position: Int): ReceptionPoint = adapter!!.filterItems[position]
}