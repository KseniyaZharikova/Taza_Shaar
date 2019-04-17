package com.tazashaar.kseniya.zerowaste.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import com.tazashaar.kseniya.zerowaste.R
import com.tazashaar.kseniya.zerowaste.interfaces.CheckBoxInterface
import com.tazashaar.kseniya.zerowaste.interfaces.SortedList
import com.tazashaar.kseniya.zerowaste.ui.activities.MainActivity
import com.tazashaar.kseniya.zerowaste.utils.Constants

import kotlinx.android.synthetic.main.fragment_chose.*


class ChoseFragment : BaseFragment(), View.OnClickListener {
    private var mCallBack: CheckBoxInterface? = null

    override fun getViewLayout(): Int {
        return R.layout.fragment_chose
    }

    override fun onClick(v: View?) {
        switchFragment()
        val tag = v!!.tag as String?
        mCallBack!!.onCheckBoxClicked(tag!!.toInt())
    }


    private fun switchFragment() {
        val fragmentManager = fragmentManager
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.container, PointsInfoFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        expandedView.setBackgroundResource(R.drawable.shape_main_view)
        initCheckbox()
        mCallBack!!.showAllPoints()
//        getHeight(view)
        //SortedList.list.clear()
    }
//    private fun getHeight(view: View) {
//
//        val observer = linearChoose.viewTreeObserver
//        observer.addOnGlobalLayoutListener {
//
//            linearChoose.viewTreeObserver.removeOnGlobalLayoutListener()
//            Constants.HIGHT_OF_LAYOUT_CHOOSE =linearChoose.height
//            Log.d("sdsdsdsdsdsd",linearChoose.height.toString())
//            val params = view.layoutParams
//            params.height = (Constants.HIGHT_OF_LAYOUT_CHOOSE * 2.8).toInt()
//            view.layoutParams = params
//
//        }
//    }

    private fun initCheckbox() {
        checkboxBottle.setOnClickListener(this)
        checkboxGlass.setOnClickListener(this)
        checkboxPaper.setOnClickListener(this)
        checkboxShirt.setOnClickListener(this)
        checkboxBag.setOnClickListener(this)
        checkboxApple.setOnClickListener(this)
        checkboxCow.setOnClickListener(this)
        checkboxMashine.setOnClickListener(this)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mCallBack = context as CheckBoxInterface
    }
}