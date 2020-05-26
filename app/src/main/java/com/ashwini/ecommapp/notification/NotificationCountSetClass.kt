package com.ashwini.ecommapp.notification

import android.app.Activity
import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.view.MenuItem

/**
 * Created by priyankam on 19-07-2016.
 */
object NotificationCountSetClass : Activity() {
    private var icon: LayerDrawable? = null
    fun setAddToCart(context: Context, item: MenuItem, numMessages: Int) {
        icon = item.icon as LayerDrawable
        SetNotificationCount.setBadgeCount(context, icon, setNotifyCount(numMessages))
    }

    @JvmStatic
    fun setNotifyCount(numMessages: Int): Int {
        return numMessages
    }
}