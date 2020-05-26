package com.ashwini.ecommapp.options

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashwini.ecommapp.R
import com.ashwini.ecommapp.fragments.ImageListFragment
import com.ashwini.ecommapp.product.ItemDetailsActivity
import com.ashwini.ecommapp.startup.MainActivity
import com.ashwini.ecommapp.utility.ImageUrlUtils
import com.facebook.drawee.view.SimpleDraweeView
import java.util.*

class CartListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list)
        mContext = this@CartListActivity
        val imageUrlUtils = ImageUrlUtils()
        val cartlistImageUri: ArrayList<String> = imageUrlUtils.cartListImageUri
        //Show cart layout based on items
        setCartLayout()
        val recyclerView = findViewById<View>(R.id.recyclerview) as RecyclerView
        val recylerViewLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(mContext)
        recyclerView.layoutManager = recylerViewLayoutManager
        recyclerView.adapter = SimpleStringRecyclerViewAdapter(recyclerView, cartlistImageUri)
    }

    protected fun setCartLayout() {
        val layoutCartItems = findViewById<View>(R.id.layout_items) as LinearLayout
        val layoutCartPayments = findViewById<View>(R.id.layout_payment) as LinearLayout
        val layoutCartNoItems = findViewById<View>(R.id.layout_cart_empty) as LinearLayout
        if (MainActivity.notificationCountCart > 0) {
            layoutCartNoItems.visibility = View.GONE
            layoutCartItems.visibility = View.VISIBLE
            layoutCartPayments.visibility = View.VISIBLE
        } else {
            layoutCartNoItems.visibility = View.VISIBLE
            layoutCartItems.visibility = View.GONE
            layoutCartPayments.visibility = View.GONE
            val bStartShopping = findViewById<View>(R.id.bAddNew) as Button
            bStartShopping.setOnClickListener { finish() }
        }
    }

    class SimpleStringRecyclerViewAdapter(private val mRecyclerView: RecyclerView, private val mCartlistImageUri: ArrayList<String>) : RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_cartlist_item, parent, false)
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
            val uri = Uri.parse(mCartlistImageUri[position])
            holder.mImageView.setImageURI(uri)
            holder.mLayoutItem.setOnClickListener {
                val intent = Intent(mContext, ItemDetailsActivity::class.java)
                intent.putExtra(ImageListFragment.STRING_IMAGE_URI, mCartlistImageUri[position])
                intent.putExtra(ImageListFragment.STRING_IMAGE_POSITION, position)
                mContext!!.startActivity(intent)
            }

            //Set click action
            holder.mLayoutRemove.setOnClickListener {
                val imageUrlUtils = ImageUrlUtils()
                imageUrlUtils.removeCartListImageUri(position)
                notifyDataSetChanged()
                //Decrease notification count
                MainActivity.notificationCountCart--
            }

            //Set click action
            holder.mLayoutEdit.setOnClickListener { }
        }

        override fun getItemCount(): Int {
            return mCartlistImageUri.size
        }

        class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
            val mImageView: SimpleDraweeView
            val mLayoutItem: LinearLayout
            val mLayoutRemove: LinearLayout
            val mLayoutEdit: LinearLayout

            init {
                mImageView = mView.findViewById<View>(R.id.image_cartlist) as SimpleDraweeView
                mLayoutItem = mView.findViewById<View>(R.id.layout_item_desc) as LinearLayout
                mLayoutRemove = mView.findViewById<View>(R.id.layout_action1) as LinearLayout
                mLayoutEdit = mView.findViewById<View>(R.id.layout_action2) as LinearLayout
            }
        }

    }

    companion object {
        private var mContext: Context? = null
    }
}