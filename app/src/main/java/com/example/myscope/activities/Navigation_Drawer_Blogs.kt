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

class Navigation_Drawer_Blogs : BaseActivity(), NavigationDrawerCallbacks, View.OnClickListener {
    private var mNavigationDrawerFragment: NavigationDrawerFragment? = null

    private var mDrawerLayout: DrawerLayout? = null
    private var toolbar: Toolbar? = null
    var navigationView: NavigationView? = null
//    private var activityTitles: Array<String> = TODO()
    private var mHandler: Handler? = null
    private var navHeader: View? = null
    private var imgNavHeaderBg: CircleImageView? = null
    var headerTextView: TextView? = null
    var linear_layout: LinearLayout? = null
    var card_medical_history: CardView? = null
    var card_prescriptions: CardView? = null
    var card_medical_documents: CardView? = null
    var card_lab_reports: CardView? = null
    var card_self_monitoring: CardView? = null
    var card_health_services: CardView? = null
    var card_educational_blog: CardView? = null
    var card_appointments: CardView? = null
    var card_contactus: CardView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_blogs)
        linear_layout = findViewById<View>(R.id.linear_layout) as LinearLayout

        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        showToolbar()
        setStatusBarTopColor()
        headerTextView = findViewById<View>(R.id.header) as TextView
        headerTextView!!.text = "DashBoard"
        card_medical_history = findViewById<View>(R.id.card_MH) as CardView
        card_prescriptions = findViewById<View>(R.id.card_Pres) as CardView
        card_medical_documents = findViewById<View>(R.id.card_MD) as CardView
        card_lab_reports = findViewById<View>(R.id.card_LabReports) as CardView
        card_self_monitoring = findViewById<View>(R.id.card_selfmonitoring) as CardView
        card_health_services = findViewById<View>(R.id.card_health_services) as CardView
        card_educational_blog = findViewById<View>(R.id.card_EducationalBlog) as CardView
        card_appointments = findViewById<View>(R.id.card_appointments) as CardView
        card_contactus = findViewById<View>(R.id.card_Contactus) as CardView
        linear_layout = findViewById<View>(R.id.linear_layout) as LinearLayout
        card_medical_history!!.setOnClickListener(this)
        card_prescriptions!!.setOnClickListener(this)
        card_medical_documents!!.setOnClickListener(this)
        card_lab_reports!!.setOnClickListener(this)
        card_self_monitoring!!.setOnClickListener(this)
        card_health_services!!.setOnClickListener(this)
        card_educational_blog!!.setOnClickListener(this)
        card_appointments!!.setOnClickListener(this)
        card_contactus!!.setOnClickListener(this)
        //
        mHandler = Handler()
        navigationView = findViewById<View>(R.id.nav_view) as NavigationView
//        activityTitles = resources.getStringArray(R.array.nav_item_activity_titles)
        toolbar!!.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        // Set up the drawer.
        mNavigationDrawerFragment = supportFragmentManager.findFragmentById(R.id.navigation_drawer) as NavigationDrawerFragment?
        //        mTitle = getTitle();
        mNavigationDrawerFragment!!.setUp(
                R.id.navigation_drawer,
                findViewById<View>(R.id.main_layout) as DrawerLayout)
        mDrawerLayout = findViewById<View>(R.id.main_layout) as DrawerLayout
        // Navigation view header
        navHeader = navigationView!!.getHeaderView(0)
        val toggle = ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer)
        mDrawerLayout!!.setDrawerListener(toggle)
        toggle.syncState()
        imgNavHeaderBg = navHeader!!.findViewById<View>(R.id.img_header_bg) as CircleImageView
        // load the store fragment by default
        navigationView!!.setBackgroundColor(resources.getColor(R.color.colorWhite))
        imgNavHeaderBg!!.setOnClickListener {
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