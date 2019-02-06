package com.tazashaar.kseniya.zerowaste.interfaces

import com.tazashaar.kseniya.zerowaste.data.ReceptionPoint

interface CheckBoxInterface {
    fun onCheckBoxClicked(tag: Int)
    fun showAllPoints()
    fun zoomCameraToMarker(item: ReceptionPoint)
    fun drawPointsByType()
    fun cameraUpdatePOintsInfo()
}