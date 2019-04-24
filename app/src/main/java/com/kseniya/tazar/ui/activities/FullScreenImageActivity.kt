package com.kseniya.tazar.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import com.kseniya.tazar.R
import com.kseniya.tazar.adapters.ImageFullAdapter
import kotlinx.android.synthetic.main.activity_full_screen_image.*

class FullScreenImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        if (intent != null) {
            val images = intent.getStringArrayListExtra("images")
            val position = intent.getIntExtra("position", 0)
            viewPagerImagesFullScreen.adapter = ImageFullAdapter(applicationContext, images, position)
            viewPagerImagesFullScreen.currentItem = position
        }
        backArrow.setOnClickListener {onBackPressed()}
    }

}
