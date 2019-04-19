package com.kseniya.tazar.ui.presenters

import com.kseniya.tazar.interfaces.ChooseInterface

class ChoosePresenter : ChooseInterface.Presenter, ChooseInterface.View {

    private var mView: ChooseInterface.View? = null


    override fun bind(view: ChooseInterface.View?) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

}