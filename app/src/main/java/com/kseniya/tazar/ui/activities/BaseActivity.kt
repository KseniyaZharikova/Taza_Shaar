package com.kseniya.tazar.ui.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import com.kseniya.tazar.R
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.util.Log
import com.kseniya.tazar.data.ReceptionPoint
import com.kseniya.tazar.ui.fragments.InfoFragment


abstract class BaseActivity : AppCompatActivity() {

    var destroyed = false


    protected fun replaceFragment(fragment: Fragment) {
        if (supportFragmentManager == null) return
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    protected fun showItemByClickMarker(point: ReceptionPoint) {
        // DishListActivity.new(this, points.id, cafe.title)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.container, InfoFragment.newInstance(point))
        if (fragmentManager.backStackEntryCount != 0)
            fragmentManager.popBackStack()
        fragmentTransaction.addToBackStack("markerInfo")

        fragmentTransaction.commit()
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)


        Log.d("asdasdd", "adadasd")
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyed = true
    }

    protected fun showSimpleAlert() {
        if (!isFinishing) {
            AlertDialog.Builder(this)
                    .setTitle(title)
                    .setCancelable(false)
                    .setMessage(getString(R.string.title_no_internet))
                    .setNegativeButton(getString(R.string.close)) { _, _ ->
                        finish()
                    }
                    .show()
        }
    }
}