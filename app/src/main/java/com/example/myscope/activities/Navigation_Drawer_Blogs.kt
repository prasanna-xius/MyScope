package com.example.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myscope.R
import com.example.myscope.fragments.NavigationDrawerFragment
import com.example.myscope.fragments.NavigationDrawerFragment.NavigationDrawerCallbacks
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_navigation_blogs.*
import kotlinx.android.synthetic.main.dashboard_screen_main.*

class Navigation_Drawer_Blogs : BaseActivity(), NavigationDrawerCallbacks, View.OnClickListener {
    private var mNavigationDrawerFragment: NavigationDrawerFragment? = null


    private var mHandler: Handler? = null
    private var navHeader: View? = null
    private var imgNavHeaderBg: CircleImageView? = null
    var headerTextView: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_blogs)

        showToolbar()
        setStatusBarTopColor()
        headerTextView = findViewById<View>(R.id.header) as TextView
        headerTextView!!.text = "DashBoard"


        card_MH!!.setOnClickListener(this)
        card_Pres!!.setOnClickListener(this)
        card_MedicalDocument!!.setOnClickListener(this)
        card_LabReports!!.setOnClickListener(this)
        card_selfMont!!.setOnClickListener(this)
        card_Health!!.setOnClickListener(this)
        card_EducationalBlog!!.setOnClickListener(this)
        card_Appoint!!.setOnClickListener(this)
        card_ContactUs!!.setOnClickListener(this)
        //
        mHandler = Handler()

        toolbar!!.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        // Set up the drawer.
        mNavigationDrawerFragment = supportFragmentManager.findFragmentById(R.id.navigation_drawer) as NavigationDrawerFragment?
        //        mTitle = getTitle();
        mNavigationDrawerFragment!!.setUp(
                R.id.navigation_drawer,
                findViewById<View>(R.id.main_layout) as DrawerLayout)

        // Navigation view header
        navHeader = nav_view!!.getHeaderView(0)
        val toggle = ActionBarDrawerToggle(
                this, main_layout, toolbar, R.string.openDrawer, R.string.closeDrawer)
        main_layout!!.setDrawerListener(toggle)
        toggle.syncState()
        imgNavHeaderBg = navHeader!!.findViewById<View>(R.id.img_header_bg) as CircleImageView
        // load the store fragment by default
        nav_view!!.setBackgroundColor(resources.getColor(R.color.colorWhite))
        imgNavHeaderBg!!.setOnClickListener {
            val intent = Intent(applicationContext, View_UserDetails_Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }
    override fun onNavigationDrawerItemSelected(position: Int) {
        // update the main content by replacing fragments
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.card_MH -> {
                navigateToActivity(Intent(applicationContext,Medical_History_Activity::class.java))
            }
            R.id.card_Pres -> {
                navigateToActivity(Intent(applicationContext,Prescription_manual::class.java))
            }
            R.id.card_MedicalDocument -> {
                navigateToActivity(Intent(applicationContext,Medical_History_Activity::class.java))
            }
            R.id.card_LabReports -> {
                navigateToActivity(Intent(applicationContext,Medical_History_Activity::class.java))
            }
            R.id.card_selfMont -> {
                navigateToActivity(Intent(applicationContext,Medical_History_Activity::class.java))
            }
            R.id.card_Health-> {
                navigateToActivity(Intent(applicationContext,Medical_History_Activity::class.java))
            }
            R.id.card_EducationalBlog -> {
                navigateToActivity(Intent(applicationContext,Medical_History_Activity::class.java))
            }
            R.id.card_Appoint -> {
                navigateToActivity(Intent(applicationContext,Medical_History_Activity::class.java))
            }
            R.id.card_ContactUs -> {
                navigateToActivity(Intent(applicationContext,Medical_History_Activity::class.java))

            }
        }
    }
}