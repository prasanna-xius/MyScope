package com.soargtechnologies.myscope.fragments

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ExpandableListView
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.ComingSoon_Activity
import com.soargtechnologies.myscope.activities.Dash_Board_Activity
import com.soargtechnologies.myscope.activities.medical_documents.Medical_Documents_HomePage
import com.soargtechnologies.myscope.activities.prescription.Prescription_manualDrugDialog
import com.soargtechnologies.myscope.activities.medical_history.*
import com.soargtechnologies.myscope.activities.self_monitering.Blood_Glucose_recyclerView
import com.soargtechnologies.myscope.activities.self_monitering.Blood_Pressure_recyclerView
import com.soargtechnologies.myscope.activities.self_monitering.Cholestrol_recyclerView
import com.soargtechnologies.myscope.activities.self_monitering.Emotional_state_recyclerView
import com.soargtechnologies.myscope.adapters.ExpandableListAdapter
import kotlinx.android.synthetic.main.activity_bmi_recycler_view.*
import java.util.*

class NavigationDrawerFragment : Fragment() {

    private var mCallbacks: NavigationDrawerCallbacks? = null
    private val toolbar: Toolbar? = null
    private val actionBarDrawerToggle: ActionBarDrawerToggle? = null
    private var mDrawerLayout: DrawerLayout? = null
    private var mDrawerListView: ExpandableListView? = null
    private var mFragmentContainerView: View? = null
    private var linear_layout: LinearLayout? = null
    private var mCurrentSelectedPosition = 0
    var groupItem = ArrayList<String>()
    var childItem = ArrayList<Any>()
    var expandableListTitle: List<String>? = null
    var expandableListDetail: HashMap<String, List<String>>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.drawer_drawer, container, false)
        mDrawerListView = view.findViewById<View>(R.id.list_slidermenu) as ExpandableListView
        mDrawerListView!!.onItemClickListener = OnItemClickListener { parent, view, position, id -> selectItem(position) }
        expandableListDetail = ExpandableListDataPump.data
        expandableListTitle = ArrayList(expandableListDetail!!.keys)
        mDrawerListView?.setAdapter(ExpandableListAdapter(activity!!, expandableListTitle!!, expandableListDetail!!))
        mDrawerListView!!.setOnGroupExpandListener { }
        mDrawerListView!!.setOnGroupClickListener { parent, v, groupPosition, id ->
            var retVal = true
            if (groupPosition == ExpandableListAdapter.Dash_Board) {
                // call some activity here
                val intent = Intent(activity, Dash_Board_Activity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else if (groupPosition == ExpandableListAdapter.Medical_History) {
                retVal = false
            } else if (groupPosition == ExpandableListAdapter.Presccriptions) {
                retVal = false
            } else if (groupPosition == ExpandableListAdapter.Medical_Documents) {
                retVal = false
            } else if (groupPosition == ExpandableListAdapter.Lab_Reports) {
                retVal = false
            } else if (groupPosition == ExpandableListAdapter.Self_Monitoring) {
                retVal = false
            } else if (groupPosition == ExpandableListAdapter.Educational_Blog) { // call some activity here
                val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else if (groupPosition == ExpandableListAdapter.Appointments) { // call some activity here
                val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else if (groupPosition == ExpandableListAdapter.Services) { // call some activity here
                val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else if (groupPosition == ExpandableListAdapter.ContactUs) { // call some activity here
                val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
            retVal
        }
        mDrawerListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            if (groupPosition == ExpandableListAdapter.Medical_History) {
                if (childPosition == ExpandableListAdapter.Medical_History1) { // call activity here
                    val intent = Intent(activity, Disease_History::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Family_History) { // call activity here
                    val intent = Intent(activity, Family_History::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Social_History) { // call activity here
                    val intent = Intent(activity, Social_History::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Diet) { // call activity here
                    val intent = Intent(activity, Diet::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Allergies) { // call activity here
                    val intent = Intent(activity, Allergies::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Immunization_History) { // call activity here
                    val intent = Intent(activity, ImmunizationHistory::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Medication_History) { // call activity here
                    val intent = Intent(activity, MedicationHistory::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Surgery_History) { // call activity here
                    val intent = Intent(activity, SurgeryHistory_Activity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Adverse_Drug_Reaction) { // call activity here
                    val intent = Intent(activity, Adverse_Drug_Reaction_Activity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
            } else if (groupPosition == ExpandableListAdapter.Presccriptions) {
                if (childPosition == ExpandableListAdapter.Prescription) { // call activity here
                    val intent = Intent(activity, Prescription_manualDrugDialog::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Antibiotic) { // call activity here
                    val intent = Intent(activity, Prescription_manualDrugDialog::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Ayurveda) { // call activity here
                    val intent = Intent(activity, Prescription_manualDrugDialog::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
            } else if (groupPosition == ExpandableListAdapter.Medical_Documents) {
                if (childPosition == ExpandableListAdapter.Discharge_Summery) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Dental_Recards) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Immuzination) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Health_Insurance) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Diet_Chart) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Education_Material) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Other_Document) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
            } else if (groupPosition == ExpandableListAdapter.Lab_Reports) {
                if (childPosition == ExpandableListAdapter.Blood_Reports) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Urine_Report) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Ultra_Sound) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.X_Ray) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.CT_Scan) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.MRI) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.ECG) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.ECHO) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Stress_Test) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.SonoGraphy) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Colono_Scopy) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Others) { // call activity here
                    val intent = Intent(activity, Medical_Documents_HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
            } else if (groupPosition == ExpandableListAdapter.Self_Monitoring) {
                if (childPosition == ExpandableListAdapter.Blood_Glucose_Monitoring) { // call activity here
                    val intent = Intent(activity, Blood_Glucose_recyclerView::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Blood_Pressure) { // call activity here
                    val intent = Intent(activity, Blood_Pressure_recyclerView::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Cholostrol) { // call activity here
                    val intent = Intent(activity, Cholestrol_recyclerView::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.BMI) { // call activity here
                    val intent = Intent(activity, bmi_recycler_view::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Exercise_Tracker) { // call activity here
                    val intent = Intent(activity, ComingSoon_Activity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else if (childPosition == ExpandableListAdapter.Emotional_State) { // call activity here
                    val intent = Intent(activity, Emotional_state_recyclerView::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
            }
            true
        }
        mDrawerListView!!.setItemChecked(mCurrentSelectedPosition, true)
        val header = inflater.inflate(R.layout.drawer_header, null)
        mDrawerListView!!.addHeaderView(header)
        return mDrawerListView
    }
    val isDrawerOpen: Boolean
        get() = mDrawerLayout != null && mDrawerLayout!!.isDrawerOpen(mFragmentContainerView!!)
    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    fun setUp(fragmentId: Int, drawerLayout: DrawerLayout?) {
        mFragmentContainerView = activity!!.findViewById(fragmentId)
        mDrawerLayout = drawerLayout
        mDrawerLayout!!.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START)
        val actionBarDrawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(activity, mDrawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }
        mDrawerLayout!!.setDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        mDrawerLayout!!.post { actionBarDrawerToggle.syncState() }
        mDrawerLayout!!.setDrawerListener(actionBarDrawerToggle)
    }
    private fun selectItem(position: Int) {
        mCurrentSelectedPosition = position
        mDrawerLayout!!.closeDrawer(linear_layout!!)
    }
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mCallbacks = try {
            activity as NavigationDrawerCallbacks
        } catch (e: ClassCastException) {
            throw ClassCastException("Activity must implement NavigationDrawerCallbacks.")
        }
    }
    override fun onDetach() {
        super.onDetach()
        mCallbacks = null
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition)
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        actionBarDrawerToggle!!.onConfigurationChanged(newConfig)
    }
    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private fun showGlobalContextActionBar() {
    }
    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        fun onNavigationDrawerItemSelected(position: Int)
    }
    companion object {
        private const val STATE_SELECTED_POSITION = "selected_navigation_drawer_position"
    }
}