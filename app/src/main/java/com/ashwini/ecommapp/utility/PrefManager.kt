package com.ashwini.ecommapp.utility

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

/**
 * Created by Lincoln on 05/05/16.
 */
class PrefManager(var _context: Context) {
    var pref: SharedPreferences
    var editor: Editor

    // shared pref mode
    var PRIVATE_MODE = 0


//    var isFirstTimeLaunch: Boolean
//        get() = pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)
//        set(isFirstTime) {
//            editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
//            editor.commit()
//        }


    fun getIsFirstTimeLaunch():Boolean{
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)
    }

    fun setIsFirstTimeLaunch(isFirstTime : Boolean): Editor? {
        return   editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
        editor.commit()
    }
    companion object {
        // Shared preferences constants
        private const val PREF_NAME = "MyPreference"
        private const val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
    }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }
}