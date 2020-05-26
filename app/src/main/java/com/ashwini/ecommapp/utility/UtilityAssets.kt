package com.ashwini.ecommapp.utility

import android.content.Context
import com.ashwini.ecommapp.model.Product
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

object UtilityAssets {
    fun loadProductsFromAsset(context: Context): ArrayList<Product>? {
        val productList = ArrayList<Product>()
        var json: String? = null
        json = try {
            val `is` = context.assets.open("ecom_items.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            val charset: Charset = Charsets.UTF_8
            String(buffer, charset)
            //String(buffer, "UTF-8")
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        try {
            val obj = JSONObject(json)
            val event_jArry = obj.getJSONArray("ecom_items")
            productList.clear()
            for (i in 0 until event_jArry.length()) {
                val jsonObject = event_jArry.getJSONObject(i)
                val event = Product()
                event.category = jsonObject.getString("Category")
                event.itemName = jsonObject.getString("Item name")
                event.price = jsonObject.getString("Price")
                event.imageName = jsonObject.getString("Image name")

                //Add your values in your `ArrayList` as below:
                productList.add(event)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return productList
    }
}