package com.ashwini.ecommapp.fragments

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class HackyViewPager : ViewPager {
    var isLocked: Boolean

    constructor(context: Context?) : super(context!!) {
        isLocked = false
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        isLocked = false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (!isLocked) {
            try {
                super.onInterceptTouchEvent(ev)
            } catch (e: IllegalArgumentException) {
                false
            }
        } else false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return !isLocked && super.onTouchEvent(event)
    }

    fun toggleLock() {
        isLocked = !isLocked
    }

}