package com.ashwini.ecommapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Product {
    @SerializedName("Category")
    @Expose
    var category: String? = null

    @SerializedName("Item name")
    @Expose
    var itemName: String? = null

    @SerializedName("Price")
    @Expose
    var price: String? = null

    @SerializedName("Image name")
    @Expose
    var imageName: String? = null

}