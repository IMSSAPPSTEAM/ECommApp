package com.ashwini.ecommapp.fragments

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ashwini.ecommapp.R
import com.ashwini.ecommapp.photoview.view.PhotoView
import com.ashwini.ecommapp.utility.ImageUrlUtils

class ViewPagerActivity : Activity() {
    private var mViewPager: ViewPager? = null
    private var position = 0
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        mViewPager = findViewById<View>(R.id.view_pager) as HackyViewPager
        setContentView(mViewPager)
        mViewPager!!.adapter = SamplePagerAdapter()
        if (intent != null) {
            position = intent.getIntExtra("position", 0)
            mViewPager!!.currentItem = position
        }
        if (savedInstanceState != null) {
            val isLocked = savedInstanceState.getBoolean(ISLOCKED_ARG, false)
            (mViewPager as HackyViewPager).isLocked
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    private val isViewPagerActive: Boolean
        private get() = mViewPager != null && mViewPager is HackyViewPager

    override fun onSaveInstanceState(outState: Bundle) {
        if (isViewPagerActive) {
            outState.putBoolean(ISLOCKED_ARG, (mViewPager as HackyViewPager?)!!.isLocked)
        }
        super.onSaveInstanceState(outState)
    }

    internal class SamplePagerAdapter : PagerAdapter() {
        override fun getCount(): Int {
            return sDrawables.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): View {
            val photoView = PhotoView(container.context)
            photoView.setImageUri(sDrawables[position])

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            return photoView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        companion object {
            /* Here I'm adding the demo pics, but you can add your Item related pics , just get your pics based on itemID (use asynctask) and
         fill the urls in arraylist*/
            private val sDrawables: Array<String> = ImageUrlUtils.getImageUrls()
        }
    }

    companion object {
        private const val ISLOCKED_ARG = "isLocked"
    }
}