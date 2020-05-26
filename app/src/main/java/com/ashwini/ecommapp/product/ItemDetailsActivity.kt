package com.ashwini.ecommapp.product

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ashwini.ecommapp.R
import com.ashwini.ecommapp.fragments.ImageListFragment
import com.ashwini.ecommapp.fragments.ViewPagerActivity
import com.ashwini.ecommapp.notification.NotificationCountSetClass.setNotifyCount
import com.ashwini.ecommapp.options.CartListActivity
import com.ashwini.ecommapp.startup.MainActivity
import com.ashwini.ecommapp.utility.ImageUrlUtils
import com.facebook.drawee.view.SimpleDraweeView

class ItemDetailsActivity : AppCompatActivity() {
    var imagePosition = 0
    var stringImageUri: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        val mImageView = findViewById<SimpleDraweeView>(R.id.image1)
        val textViewAddToCart = findViewById<TextView>(R.id.text_action_bottom1)
        val textViewBuyNow = findViewById<TextView>(R.id.text_action_bottom2)

        //Getting image uri from previous screen
        if (intent != null) {
            stringImageUri = intent.getStringExtra(ImageListFragment.STRING_IMAGE_URI)
            imagePosition = intent.getIntExtra(ImageListFragment.STRING_IMAGE_URI, 0)
        }
        val uri = Uri.parse(stringImageUri)
        mImageView.setImageURI(uri)
        mImageView.setOnClickListener {
            val intent = Intent(this@ItemDetailsActivity, ViewPagerActivity::class.java)
            intent.putExtra("position", imagePosition)
            startActivity(intent)
        }
        textViewAddToCart.setOnClickListener {
            val imageUrlUtils = ImageUrlUtils()
            imageUrlUtils.addCartListImageUri(stringImageUri)
            Toast.makeText(this@ItemDetailsActivity, "Item added to cart.", Toast.LENGTH_SHORT).show()
            MainActivity.notificationCountCart++
            setNotifyCount(MainActivity.notificationCountCart)
        }
        textViewBuyNow.setOnClickListener {
            val imageUrlUtils = ImageUrlUtils()
            imageUrlUtils.addCartListImageUri(stringImageUri)
            MainActivity.notificationCountCart++
            setNotifyCount(MainActivity.notificationCountCart)
            startActivity(Intent(this@ItemDetailsActivity, CartListActivity::class.java))
        }
    }
}