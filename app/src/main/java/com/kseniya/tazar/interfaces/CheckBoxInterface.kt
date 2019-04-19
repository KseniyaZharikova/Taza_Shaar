package com.kseniya.tazar.interfaces

import com.kseniya.tazar.data.ReceptionPoint

interface CheckBoxInterface {
    fun onCheckBoxClicked(tag: Int)
    fun showAllPoints()
    fun zoomCameraToMarker(item: ReceptionPoint)
    fun drawPointsByType()
    fun cameraUpdatePOintsInfo()
}