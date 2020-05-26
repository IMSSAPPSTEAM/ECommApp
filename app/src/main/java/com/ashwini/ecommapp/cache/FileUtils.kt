package com.ashwini.ecommapp.cache

import java.io.InputStream
import java.io.OutputStream

/**
 * Created by 06peng on 2015-07-03.
 */
object FileUtils {
    fun CopyStream(`is`: InputStream, os: OutputStream) {
        val buffer_size = 1024
        try {
            val bytes = ByteArray(buffer_size)
            while (true) {
                val count = `is`.read(bytes, 0, buffer_size)
                if (count == -1) break
                os.write(bytes, 0, count)
            }
        } catch (ex: Exception) {
        }
    }
}