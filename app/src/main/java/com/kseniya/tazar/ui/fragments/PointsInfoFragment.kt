package com.kseniya.tazar.ui.fragments
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.kseniya.tazar.R
import com.kseniya.tazar.interfaces.CheckBoxInterface
import com.kseniya.tazar.interfaces.SortedList
import com.kseniya.tazar.ui.presenters.PointsInfoPresenter
import kotlinx.android.synthetic.main.fragment_points_info.*

class PointsInfoFragment : BaseFragment(), View.OnClickListener, SortedList {


    var presenter = PointsInfoPresenter()
    lateinit var mCallBack: CheckBoxInterface
    private var mIsShowingCardHeaderShadow: Boolean = false
    override fun getViewLayout(): Int {
        return R.layout.fragment_points_info
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lm = LinearLayoutManager(context)
        card_recyclerview.layoutManager = lm
        if ( SortedList.list.size == 0){
            nestedscrollview.visibility = View.GONE
            showAlert(resources.getString(R.string.attention), resources.getString(R.string.noPoints))
        }else{

            val arrayTypes = resources.getStringArray(R.array.type_names)
            val nameType  = arrayTypes[SortedList.list[0].type.toInt()-1]
            card_title.text = "Прием $nameType:"
            go_back.setOnClickListener(this)
            presenter.bind(this, SortedList.list!!)
            presenter.bindRecyclerView(card_recyclerview)
            card_recyclerview.addItemDecoration(DividerItemDecoration(context, 0))

            card_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
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
                    card_recyclerview.scrollToPosition(0)
                    cardview.alpha = 1.0f
                    mIsShowingCardHeaderShadow = false
                }
            })

            mCallBack.drawPointsByType()
            mCallBack.cameraUpdatePOintsInfo()
        }
    }


    private fun showOrHideView(view: View, shouldShow: Boolean) {
        view.animate().alpha(if (shouldShow) 1f else 0f)
                .setDuration(100).interpolator = DecelerateInterpolator()
    }

    private fun showAlert(title: String, message: String) {

            AlertDialog.Builder(context!!)
                    .setTitle(title)
                    .setCancelable(false)
                    .setMessage(message)
                    .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                        fragmentManager!!.popBackStack()
                    }
                    .show()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mCallBack = context as CheckBoxInterface
    }

    override fun onClickItem(position: Int) {

        val point = presenter.pointsForPosition(position)
        val fragmentManager = fragmentManager
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.container, InfoFragment.newInstance(point))
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commitAllowingStateLoss()
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    }

    override fun onClick(v: View?) {
        fragmentManager!!.popBackStack()
    }


    override fun setNoResultVisible(isEmpty: Boolean) {

    }
}