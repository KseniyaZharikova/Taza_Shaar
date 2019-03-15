package com.tazashaar.kseniya.zerowaste.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tazashaar.kseniya.zerowaste.R
import com.tazashaar.kseniya.zerowaste.adapters.ImageFullAdapter
import kotlinx.android.synthetic.main.activity_full_screen_image.*

class FullScreenImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)

        if (intent != null) {
            val images = intent.getStringArrayListExtra("images")
            val position = intent.getIntExtra("position", 0)
            viewPagerImagesFullScreen.adapter = ImageFullAdapter(applicationContext, images, position)
            viewPagerImagesFullScreen.currentItem = position
        }
        backArrow.setOnClickListener {onBackPressed()}
    }

}
