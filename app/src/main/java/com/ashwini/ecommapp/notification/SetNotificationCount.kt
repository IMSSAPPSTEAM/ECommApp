package com.ashwini.ecommapp.notification

import android.content.Context
import android.graphics.drawable.LayerDrawable
import com.ashwini.ecommapp.R

/**
 * Created by priyankam on 4/13/2016.
 */
object SetNotificationCount {
    fun setBadgeCount(context: Context, icon: LayerDrawable?, count: Int) {
        val badge: BadgeDrawable

        // Reuse drawable if possible
        val reuse = icon!!.findDrawableByLayerId(R.id.ic_badge)
        badge = if (reuse != null && reuse is BadgeDrawable) {
            reuse
        } else {
            BadgeDrawable(context)
        }
        badge.setCount(count)
        icon.mutate()
        icon.setDrawableByLayerId(R.id.ic_badge, badge)
    }
}