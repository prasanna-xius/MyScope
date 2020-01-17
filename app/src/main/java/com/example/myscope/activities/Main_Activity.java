package com.example.myscope.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myscope.R;
import com.example.myscope.fragments.ContactUs_Fragment;
import com.example.myscope.fragments.Feedback_Fragment;
import com.example.myscope.fragments.Home_Fragment;
import com.example.myscope.fragments.Notifications_Fragment;
import com.example.myscope.fragments.Profile_History_Fragment;
import com.example.myscope.fragments.Services_Fragment;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;


public class Main_Activity extends BaseActivity{

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private CircleImageView imgNavHeaderBg;
    CircleImageView imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;

    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME= "home";
    private static final String TAG_NOTIFICATION = "notification";

    private static final String TAG_PROFILE_HISTORY = "profileHistory";
    private static final String TAG_SERVICES = "services";
    private static final String TAG_FEEDBACK = "feedback";

    private static final String TAG_CONTACTUS = "contactus";
    public static String CURRENT_TAG = TAG_HOME;

    private String[] activityTitles;

    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
