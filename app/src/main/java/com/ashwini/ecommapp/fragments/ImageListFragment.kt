package com.ashwini.ecommapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ashwini.ecommapp.R
import com.ashwini.ecommapp.model.Product
import com.ashwini.ecommapp.product.ItemDetailsActivity
import com.ashwini.ecommapp.startup.MainActivity
import com.ashwini.ecommapp.utility.ImageUrlUtils
import com.ashwini.ecommapp.utility.UtilityAssets
import com.facebook.drawee.view.SimpleDraweeView
import java.util.*

class ImageListFragment : Fragment() {
    private var products: ArrayList<Product>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as MainActivity?
        products = UtilityAssets.loadProductsFromAsset(requireActivity())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rv = inflater.inflate(R.layout.layout_recylerview_list, container, false) as RecyclerView
        setupRecyclerView(rv)
        return rv
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        /*  if (ImageListFragment.this.getArguments().getInt("type") == 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        } else if (ImageListFragment.this.getArguments().getInt("type") == 2) {
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            GridLayoutManager layoutManager = new GridLayoutManager(recyclerView.getContext(), 3);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
        }*/
        var items: Array<String>? = null
        items = if (this@ImageListFragment.arguments!!.getInt("type") == 1) {
            ImageUrlUtils.getGroceryUrls()
        } else if (this@ImageListFragment.arguments!!.getInt("type") == 2) {
            ImageUrlUtils.getDairyBeveragesUrls()
        } else if (this@ImageListFragment.arguments!!.getInt("type") == 3) {
            ImageUrlUtils.getPackagedFoodUrls()
        } else if (this@ImageListFragment.arguments!!.getInt("type") == 4) {
            ImageUrlUtils.getHomeApplianceUrls()
        } else if (this@ImageListFragment.arguments!!.getInt("type") == 5) {
            ImageUrlUtils.getBooksUrls()
        } else {
            ImageUrlUtils.getImageUrls()
        }
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = SimpleStringRecyclerViewAdapter(recyclerView, products)
    }

    class SimpleStringRecyclerViewAdapter(private val mRecyclerView: RecyclerView, private val mProducts: ArrayList<Product>?) : RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
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
            /* FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.mImageView.getLayoutParams();
            if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
                layoutParams.height = 200;
            } else if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                layoutParams.height = 600;
            } else {
                layoutParams.height = 800;
            }*/
            val product = mProducts!![position]
            val uri = Uri.parse(product.imageName)
            holder.mImageView.setImageURI(uri)
            holder.mItemName.text = product.itemName
            holder.mItemPrice.text = product.price
            holder.mLayoutItem.setOnClickListener {
                val intent = Intent(mActivity, ItemDetailsActivity::class.java)
                intent.putExtra(STRING_IMAGE_URI, product.imageName)
                intent.putExtra(STRING_IMAGE_POSITION, position)
                mActivity!!.startActivity(intent)
            }

            //Set click action for wishlist
            holder.mImageViewWishlist.setOnClickListener {
                val imageUrlUtils = ImageUrlUtils()
                imageUrlUtils.addWishlistImageUri(product.imageName)
                holder.mImageViewWishlist.setImageResource(R.drawable.ic_favorite_black_18dp)
                notifyDataSetChanged()
                Toast.makeText(mActivity, "Item added to wish list.", Toast.LENGTH_SHORT).show()
            }
        }

        override fun getItemCount(): Int {
            return mProducts!!.size
        }

        class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
            val mImageView: SimpleDraweeView
            val mLayoutItem: LinearLayout
            val mImageViewWishlist: ImageView
            val mItemName: TextView
            val mItemDesc: TextView
            val mItemPrice: TextView

            init {
                mImageView = mView.findViewById<View>(R.id.image1) as SimpleDraweeView
                mLayoutItem = mView.findViewById<View>(R.id.layout_item) as LinearLayout
                mImageViewWishlist = mView.findViewById<View>(R.id.ic_wishlist) as ImageView
                mItemName = mView.findViewById<View>(R.id.item_name) as TextView
                mItemDesc = mView.findViewById<View>(R.id.item_desc) as TextView
                mItemPrice = mView.findViewById<View>(R.id.item_price) as TextView
            }
        }

    }

    companion object {
        const val STRING_IMAGE_URI = "ImageUri"
        const val STRING_IMAGE_POSITION = "ImagePosition"
        private var mActivity: MainActivity? = null
    }
}