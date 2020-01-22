package com.example.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myscope.R
import com.example.myscope.fragments.NavigationDrawerFragment
import com.example.myscope.fragments.NavigationDrawerFragment.NavigationDrawerCallbacks
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_navigation_blogs.*
import kotlinx.android.synthetic.main.dashboard_screen_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class Navigation_Drawer_Blogs : BaseActivity(), NavigationDrawerCallbacks, View.OnClickListener {

    private var mNavigationDrawerFragment: NavigationDrawerFragment? = null

    private var mDrawerLayout: DrawerLayout? = null
    private var toolbar: Toolbar? = null
    private var mHandler: Handler? = null
    private var navHeader: View? = null
    private var imgNavHeaderBg: CircleImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_blogs)

        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        showToolbar()
        setStatusBarTopColor()
        header!!.text = "DashBoard"

        card_MH!!.setOnClickListener(this)
        card_Pres!!.setOnClickListener(this)
        card_MD!!.setOnClickListener(this)
        card_LabReports!!.setOnClickListener(this)
        card_selfmonitoring!!.setOnClickListener(this)
        card_health_services!!.setOnClickListener(this)
        card_EducationalBlog!!.setOnClickListener(this)
        card_appointments!!.setOnClickListener(this)
        card_Contactus!!.setOnClickListener(this)
        //
        mHandler = Handler()

        toolbar!!.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        // Set up the drawer.
        mNavigationDrawerFragment = supportFragmentManager.findFragmentById(R.id.navigation_drawer) as NavigationDrawerFragment?
        //        mTitle = getTitle();
        mNavigationDrawerFragment!!.setUp(R.id.navigation_drawer, findViewById<View>(R.id.main_layout) as DrawerLayout)
        mDrawerLayout = findViewById<View>(R.id.main_layout) as DrawerLayout
        // Navigation view header
        navHeader = nav_view!!.getHeaderView(0)
        val toggle = ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer)
        mDrawerLayout!!.setDrawerListener(toggle)
        toggle.syncState()
        // load the store fragment by default
        nav_view!!.setBackgroundColor(resources.getColor(R.color.colorWhite))
        img_header_bg!!.setOnClickListener {
            val intent = Intent(applicationContext, View_UserDetails_Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onNavigationDrawerItemSelected(position: Int) { // update the main content by replacing fragments
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.card_MH -> {
                val card_mh_intent = Intent(applicationContext, Medical_History_Activity::class.java)
                card_mh_intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(card_mh_intent)
            }
            R.id.card_Pres -> {
                val card_press_intent = Intent(applicationContext, Medical_History_Activity::class.java)
                card_press_intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(card_press_intent)
            }
            R.id.card_MD -> {
                val card_md_intent = Intent(applicationContext, Medical_History_Activity::class.java)
                card_md_intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(card_md_intent)
            }
            R.id.card_LabReports -> {
                val card_labreports_intent = Intent(applicationContext, Medical_History_Activity::class.java)
                card_labreports_intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(card_labreports_intent)
            }
            R.id.card_selfmonitoring -> {
                val card_selfmonitoring_intent = Intent(applicationContext, Medical_History_Activity::class.java)
                card_selfmonitoring_intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(card_selfmonitoring_intent)
            }
            R.id.card_health_services -> {
                val card_health_intent = Intent(applicationContext, Medical_History_Activity::class.java)
                card_health_intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(card_health_intent)
            }
            R.id.card_EducationalBlog -> {
                val card_educational_intent = Intent(applicationContext, Medical_History_Activity::class.java)
                card_educational_intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(card_educational_intent)
            }
            R.id.card_appointments -> {
                val card_appointments_intent = Intent(applicationContext, Medical_History_Activity::class.java)
                card_appointments_intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(card_appointments_intent)
            }
            R.id.card_Contactus -> {
                val card_contactus_intent = Intent(applicationContext, Medical_History_Activity::class.java)
                card_contactus_intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(card_contactus_intent)
            }
        }
    }
}