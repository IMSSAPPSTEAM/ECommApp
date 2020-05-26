package com.ashwini.ecommapp.startup

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ashwini.ecommapp.R
import com.ashwini.ecommapp.fragments.ImageListFragment
import com.ashwini.ecommapp.miscellaneous.EmptyActivity
import com.ashwini.ecommapp.notification.NotificationCountSetClass
import com.ashwini.ecommapp.options.CartListActivity
import com.ashwini.ecommapp.options.SearchResultActivity
import com.ashwini.ecommapp.options.WishlistActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        viewPager = findViewById<View>(R.id.viewpager) as ViewPager
        tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        if (viewPager != null) {
            setupViewPager(viewPager)
            tabLayout!!.setupWithViewPager(viewPager)
        }


        /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        val item = menu.findItem(R.id.action_cart)
        NotificationCountSetClass.setAddToCart(this@MainActivity, item, notificationCountCart)
        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu()
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_search) {
            startActivity(Intent(this@MainActivity, SearchResultActivity::class.java))
            return true
        } else if (id == R.id.action_cart) {

            /* NotificationCountSetClass.setAddToCart(MainActivity.this, item, notificationCount);
            invalidateOptionsMenu();*/
            startActivity(Intent(this@MainActivity, CartListActivity::class.java))

            /* notificationCount=0;//clear notification count
            invalidateOptionsMenu();*/return true
        } else {
            startActivity(Intent(this@MainActivity, EmptyActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val adapter = Adapter(supportFragmentManager)
        var fragment = ImageListFragment()
        var bundle = Bundle()
        bundle.putInt("type", 1)
        fragment.arguments = bundle
        adapter.addFragment(fragment, getString(R.string.item_1))
        fragment = ImageListFragment()
        bundle = Bundle()
        bundle.putInt("type", 2)
        fragment.arguments = bundle
        adapter.addFragment(fragment, getString(R.string.item_2))
        fragment = ImageListFragment()
        bundle = Bundle()
        bundle.putInt("type", 3)
        fragment.arguments = bundle
        adapter.addFragment(fragment, getString(R.string.item_3))
        fragment = ImageListFragment()
        bundle = Bundle()
        bundle.putInt("type", 4)
        fragment.arguments = bundle
        adapter.addFragment(fragment, getString(R.string.item_4))
        fragment = ImageListFragment()
        bundle = Bundle()
        bundle.putInt("type", 5)
        fragment.arguments = bundle
        adapter.addFragment(fragment, getString(R.string.item_5))
        fragment = ImageListFragment()
        bundle = Bundle()
        bundle.putInt("type", 6)
        fragment.arguments = bundle
        adapter.addFragment(fragment, getString(R.string.item_6))
        viewPager!!.adapter = adapter
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        if (id == R.id.nav_item1) {
            viewPager!!.currentItem = 0
        } else if (id == R.id.nav_item2) {
            viewPager!!.currentItem = 1
        } else if (id == R.id.nav_item3) {
            viewPager!!.currentItem = 2
        } else if (id == R.id.nav_item4) {
            viewPager!!.currentItem = 3
        } else if (id == R.id.nav_item5) {
            viewPager!!.currentItem = 4
        } else if (id == R.id.nav_item6) {
            viewPager!!.currentItem = 5
        } else if (id == R.id.my_wishlist) {
            startActivity(Intent(this@MainActivity, WishlistActivity::class.java))
        } else if (id == R.id.my_cart) {
            startActivity(Intent(this@MainActivity, CartListActivity::class.java))
        } else {
            startActivity(Intent(this@MainActivity, EmptyActivity::class.java))
        }
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    internal class Adapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
        private val mFragments: MutableList<Fragment> = ArrayList()
        private val mFragmentTitles: MutableList<String> = ArrayList()
        fun addFragment(fragment: Fragment, title: String) {
            mFragments.add(fragment)
            mFragmentTitles.add(title)
        }

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitles[position]
        }
    }

    companion object {
        @JvmField
        var notificationCountCart = 0
        var viewPager: ViewPager? = null
        var tabLayout: TabLayout? = null
    }
}