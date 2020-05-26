package com.ashwini.ecommapp.startup

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.ashwini.ecommapp.R

class SplashActivity : Activity(), Animation.AnimationListener {
    var animFadeIn: Animation? = null
    var linearLayout: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            val decorView = window.decorView
            // Hide the status bar.
            val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
            decorView.systemUiVisibility = uiOptions
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
        }
        // load the animation
        animFadeIn = AnimationUtils.loadAnimation(applicationContext,
                R.anim.animation_fade_in)
        // set animation listener
        animFadeIn?.setAnimationListener(this)

        // animation for image
        linearLayout = findViewById<View>(R.id.layout_linear) as LinearLayout
        // start the animation
        linearLayout!!.visibility = View.VISIBLE
        linearLayout!!.startAnimation(animFadeIn)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun onAnimationStart(animation: Animation) {
        //under Implementation
    }

    override fun onAnimationEnd(animation: Animation) {
        // Start Main Screen
        val i = Intent(this@SplashActivity, WelcomeActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onAnimationRepeat(animation: Animation) {
        //under Implementation
    }
}