package com.ashwini.ecommapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Products {
    @SerializedName("products")
    @Expose
    var products: List<Product>? = null

}