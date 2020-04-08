package com.example.myscope.activities;


import android.os.Bundle;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.example.myscope.R;

import java.util.ArrayList;
import java.util.List;

public class Image_Activity extends BaseActivity {
    int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set title for the ViewPager
        setTitle("ViewPager");
        // Get the view from view_pager.xml
        setContentView(R.layout.images_main);

        // Loop through the ids to create a list of full screen image views
        Educational_Image_Adapter imageAdapter1 = new Educational_Image_Adapter();
        List<ImageView> images = new ArrayList<ImageView>();

        for (int i = 0; i < imageAdapter1.getCount(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageAdapter1.mThumbIds[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            images.add(imageView);
        }

        // Finally create the adapter
        ViewPager_Adapter imagePagerAdapter = new ViewPager_Adapter(images);
        ViewPager viewPager = (ViewPager) findViewById(R.id.fullimage);
        viewPager.setAdapter(imagePagerAdapter);

        // Set the ViewPager to point to the selected image from the previous activity
        // Selected image id
        int position = getIntent().getExtras().getInt("id");
        viewPager.setCurrentItem(position);
    }
}
