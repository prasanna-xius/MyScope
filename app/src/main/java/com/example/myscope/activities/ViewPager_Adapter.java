package com.example.myscope.activities;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ViewPager_Adapter extends PagerAdapter {

    private List<ImageView> images;

    public ViewPager_Adapter(List<ImageView> images) {
        this.images = images;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = images.get(position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(images.get(position));
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
