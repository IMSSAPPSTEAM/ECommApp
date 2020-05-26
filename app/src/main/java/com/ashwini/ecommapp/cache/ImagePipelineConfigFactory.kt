/*
 * This file provided by Facebook is for non-commercial testing and evaluation
 * purposes only.  Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.ashwini.ecommapp.cache

import android.content.Context
import android.os.Build
import android.os.Environment
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.util.ByteConstants
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.core.ImagePipelineConfig
import java.io.File

/**
 * Creates ImagePipeline configuration for the sample app
 */
object ImagePipelineConfigFactory {
    const val MAX_DISK_CACHE_SIZE = 300 * ByteConstants.MB
    private const val IMAGE_PIPELINE_CACHE_DIR = "imagepipeline_cache"

    //    private static ImagePipelineConfig sOkHttpImagePipelineConfig;
    private val MAX_HEAP_SIZE = Runtime.getRuntime().maxMemory().toInt()
    val MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 3
    private var sImagePipelineConfig: ImagePipelineConfig? = null

    /**
     * Creates config using android http stack as network backend.
     */
    @JvmStatic
    fun getImagePipelineConfig(context: Context): ImagePipelineConfig? {
        if (sImagePipelineConfig == null) {
            val configBuilder = ImagePipelineConfig.newBuilder(context)
            configureCaches(configBuilder, context)
            sImagePipelineConfig = configBuilder.build()
        }
        return sImagePipelineConfig
    }
    /**
     * Creates config using OkHttp as network backed.
     */
    /*  public static ImagePipelineConfig getOkHttpImagePipelineConfig(Context context) {
    if (sOkHttpImagePipelineConfig == null) {
      OkHttpClient okHttpClient = new OkHttpClient();
      ImagePipelineConfig.Builder configBuilder =
        OkHttpImagePipelineConfigFactory.newBuilder(context, okHttpClient);
      configureCaches(configBuilder, context);
      sOkHttpImagePipelineConfig = configBuilder.build();
    }
    return sOkHttpImagePipelineConfig;
  }*/
    /**
     * Configures disk and memory cache not to exceed common limits
     */
    private fun configureCaches(configBuilder: ImagePipelineConfig.Builder, context: Context) {
        val bitmapCacheParams = MemoryCacheParams(
                MAX_MEMORY_CACHE_SIZE, Int.MAX_VALUE,  // Max entries in the cache
                MAX_MEMORY_CACHE_SIZE, Int.MAX_VALUE, Int.MAX_VALUE) // Max cache entry size
        configBuilder
                .setBitmapMemoryCacheParamsSupplier { bitmapCacheParams }
                .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(context)
                        .setBaseDirectoryPath(getExternalCacheDir(context))
                        .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)
                        .setMaxCacheSize(MAX_DISK_CACHE_SIZE.toLong())
                        .build())
    }

    fun getExternalCacheDir(context: Context): File? {
        if (hasExternalCacheDir()) return context.externalCacheDir

        // Before Froyo we need to construct the external cache dir ourselves
        val cacheDir = "/Android/data/" + context.packageName + "/cache/"
        return createFile(Environment.getExternalStorageDirectory().path + cacheDir, "")
    }

    fun hasExternalCacheDir(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO
    }

    fun createFile(folderPath: String?, fileName: String?): File {
        val destDir = File(folderPath)
        if (!destDir.exists()) {
            destDir.mkdirs()
        }
        return File(folderPath, fileName)
    }
}