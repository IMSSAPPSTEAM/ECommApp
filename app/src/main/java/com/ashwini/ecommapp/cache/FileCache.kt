package com.ashwini.ecommapp.cache

import android.content.Context
import android.os.Environment
import java.io.File

/**
 * Created by 06peng on 2015-07-03.
 */
class FileCache(context: Context) {
    var cacheDir: File? = null

    fun getFile(url: String): File? {
        val filename = url.hashCode().toString()
        val f = File(cacheDir, filename)
        return if (f.exists()) {
            f
        } else null
    }

    fun clear() {
        val files = cacheDir!!.listFiles() ?: return
        for (f in files) {
            f.delete()
        }
    }

    init {
        //Find the dir to save cached images
        cacheDir = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            File(Environment.getExternalStorageDirectory(), "fresco_cache")
        } else {
            context.cacheDir
        }
        if (!cacheDir!!.exists()) {
            cacheDir!!.mkdirs()
        }
    }
}