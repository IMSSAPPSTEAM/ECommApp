package com.ashwini.ecommapp.options

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashwini.ecommapp.R
import com.ashwini.ecommapp.fragments.ImageListFragment
import com.ashwini.ecommapp.product.ItemDetailsActivity
import com.ashwini.ecommapp.utility.ImageUrlUtils
import com.facebook.drawee.view.SimpleDraweeView
import java.util.*

class WishlistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_recylerview_list)
        mContext = this@WishlistActivity
        val imageUrlUtils = ImageUrlUtils()
        val wishlistImageUri: ArrayList<String> = imageUrlUtils.cartListImageUri
        val recyclerView = findViewById<View>(R.id.recyclerview) as RecyclerView
        val recylerViewLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(mContext)
        recyclerView.layoutManager = recylerViewLayoutManager
        recyclerView.adapter = SimpleStringRecyclerViewAdapter(recyclerView, wishlistImageUri)
    }

    class SimpleStringRecyclerViewAdapter(private val mRecyclerView: RecyclerView, private val mWishlistImageUri: ArrayList<String>) : RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_wishlist_item, parent, false)
            return ViewHolder(view)
        }

        override fun onViewRecycled(holder: ViewHolder) {
            if (holder.mImageView.controller != null) {
                holder.mImageView.controller!!.onDetach()
            }
            if (holder.mImageView.topLevelDrawable != null) {
                holder.mImageView.topLevelDrawable!!.callback = null
                //                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val uri = Uri.parse(mWishlistImageUri[position])
            holder.mImageView.setImageURI(uri)
            holder.mLayoutItem.setOnClickListener {
                val intent = Intent(mContext, ItemDetailsActivity::class.java)
                intent.putExtra(ImageListFragment.STRING_IMAGE_URI, mWishlistImageUri[position])
                intent.putExtra(ImageListFragment.STRING_IMAGE_POSITION, position)
                mContext!!.startActivity(intent)
            }

            //Set click action for wishlist
            holder.mImageViewWishlist.setOnClickListener {
                val imageUrlUtils = ImageUrlUtils()
                imageUrlUtils.removeWishlistImageUri(position)
                notifyDataSetChanged()
            }
        }

        override fun getItemCount(): Int {
            return mWishlistImageUri.size
        }

        class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
            val mImageView: SimpleDraweeView
            val mLayoutItem: LinearLayout
            val mImageViewWishlist: ImageView

            init {
                mImageView = mView.findViewById<View>(R.id.image_wishlist) as SimpleDraweeView
                mLayoutItem = mView.findViewById<View>(R.id.layout_item_desc) as LinearLayout
                mImageViewWishlist = mView.findViewById<View>(R.id.ic_wishlist) as ImageView
            }
        }

    }

    companion object {
        private var mContext: Context? = null
    }
}