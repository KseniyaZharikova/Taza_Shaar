package com.tazashaar.kseniya.zerowaste.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.tazashaar.kseniya.zerowaste.R
import com.tazashaar.kseniya.zerowaste.adapters.PointDetailsAdapter
import com.tazashaar.kseniya.zerowaste.data.ReceptionPoint
import com.tazashaar.kseniya.zerowaste.interfaces.CheckBoxInterface
import kotlinx.android.synthetic.main.info_fragment.*



class InfoFragment : BaseFragment(), View.OnClickListener {

    var mCallBack: CheckBoxInterface? = null
    var item: ReceptionPoint? = null
    private var mIsShowingCardHeaderShadow: Boolean = false


    companion object {
        fun newInstance(item: ReceptionPoint): InfoFragment {
            val fragment = InfoFragment()
            val bundle = Bundle()
            bundle.putSerializable("item", item)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getViewLayout(): Int {
        return R.layout.info_fragment

    }

    private fun parseStringImages(images: String): List<String> {
        var list: List<String> = ArrayList()
        if (images.isEmpty()) return list else {
            list = images.split(",")
        }
        return list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        item = arguments!!.getSerializable("item") as ReceptionPoint
        card_title.text = item!!.name

        back_arrow_button_info.setOnClickListener(this)
        go_back.setOnClickListener(this)


        val lm = LinearLayoutManager(context)
        card_recyclerview.layoutManager = lm
        parseStringImages(item!!.images)
        card_recyclerview.adapter = PointDetailsAdapter(item!!, parseStringImages(item!!.images))
        card_recyclerview.addItemDecoration(DividerItemDecoration(context, 0))

        card_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                // Animate the shadow view in/out as the user scrolls so that it
                // looks like the RecyclerView is scrolling beneath the card header.
                val isRecyclerViewScrolledToTop = lm.findFirstVisibleItemPosition() == 0 && lm.findViewByPosition(0)!!.top == 0
                if (!isRecyclerViewScrolledToTop && !mIsShowingCardHeaderShadow) {
                    mIsShowingCardHeaderShadow = true
                    showOrHideView(cardview, true)
                } else if (isRecyclerViewScrolledToTop && mIsShowingCardHeaderShadow) {
                    mIsShowingCardHeaderShadow = false
                    showOrHideView(cardview, false)
                }
            }
        })

        nestedscrollview.overScrollMode = View.OVER_SCROLL_NEVER
        nestedscrollview.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY == 0 && oldScrollY > 0) {
                // Reset the RecyclerView's scroll position each time the card
                // returns to its starting position.
                card_recyclerview.scrollToPosition(0)
                cardview.alpha = 1.0f
                mIsShowingCardHeaderShadow = false
            }
        })
        mCallBack!!.zoomCameraToMarker(item!!)
    }

    private fun showOrHideView(view: View, shouldShow: Boolean) {
        view.animate().alpha(if (shouldShow) 1f else 0f)
                .setDuration(100).interpolator = DecelerateInterpolator()
    }

    override fun onClick(v: View?) {
        fragmentManager!!.popBackStack()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mCallBack = context as CheckBoxInterface
    }

}