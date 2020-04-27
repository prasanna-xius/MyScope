package com.soargtechnologies.myscope.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.labReports.Lab_Reports_Homepage
import com.soargtechnologies.myscope.activities.medical_documents.Medical_Documents_HomePage
import com.soargtechnologies.myscope.activities.medical_history.Medical_History_HomePage
import com.soargtechnologies.myscope.activities.medical_history.health_services.Services_medical_history
import com.soargtechnologies.myscope.activities.prescription.Prescriptions_HomePage
import com.soargtechnologies.myscope.fragments.NavigationDrawerFragment
import com.soargtechnologies.myscope.fragments.NavigationDrawerFragment.NavigationDrawerCallbacks
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

        navigationToolbar()
        headerTextView = findViewById<View>(R.id.header) as TextView
        headerTextView!!.text = "DashBoard"

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        card_MH!!.setOnClickListener(this)
        card_Pres!!.setOnClickListener(this)
        card_MedicalDocument!!.setOnClickListener(this)
        card_LabReports!!.setOnClickListener(this)
        card_selfMont!!.setOnClickListener(this)
        card_HealthServices!!.setOnClickListener(this)
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
        return if (id == R.id.action_Logout) {
            doExit()
            true
        } else super.onOptionsItemSelected(item)
    }
    override fun onNavigationDrawerItemSelected(position: Int) {
        // update the main content by replacing fragments
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.card_MH -> {
                navigateToActivity(Intent(applicationContext, Medical_History_HomePage::class.java))
            }
            R.id.card_Pres -> {
                navigateToActivity(Intent(applicationContext, Prescriptions_HomePage::class.java))
            }

            R.id.card_MedicalDocument -> {
                navigateToActivity(Intent(applicationContext,Medical_Documents_HomePage::class.java))
            }
            R.id.card_LabReports -> {
                navigateToActivity(Intent(applicationContext,Lab_Reports_Homepage::class.java))
            }
            R.id.card_selfMont -> {
                navigateToActivity(Intent(applicationContext,ComingSoon_Activity::class.java))
            }
            R.id.card_HealthServices-> {
                navigateToActivity(Intent(applicationContext,Services_medical_history::class.java))
            }
            R.id.card_EducationalBlog -> {
                navigateToActivity(Intent(applicationContext,Educational_Blog_Activity ::class.java))
            }
            R.id.card_Appoint -> {
                navigateToActivity(Intent(applicationContext,ComingSoon_Activity::class.java))
            }
            R.id.card_ContactUs -> {
                navigateToActivity(Intent(applicationContext,Contact_Us::class.java))
            }
        }
    }
    override fun onBackPressed() {
        doExit()
    }

    private fun doExit() {
        val alertDialog = AlertDialog.Builder(
                this@Navigation_Drawer_Blogs)
        alertDialog.setPositiveButton("Yes") { dialog, which ->

            FirebaseAuth.getInstance().signOut()
//            Toast.makeText(this@Navigation_Drawer_Blogs, "Logout Successfully Completed", Toast.LENGTH_SHORT).show()
            navigateToActivity(Intent(applicationContext,Login_Page::class.java))
//            finish()
        }

        alertDialog.setNegativeButton("No", null)
        alertDialog.setMessage("Do you want to Logout?")
        alertDialog.setTitle("MyScope")
        val dialog = alertDialog.create()
        dialog.show()
        dialog.window!!.setBackgroundDrawableResource(R.color.colorBackground)
        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
        positiveButton.setTextColor(Color.parseColor("#ffffffff"))
        negativeButton.setTextColor(Color.parseColor("#ffffffff"))

    }
}