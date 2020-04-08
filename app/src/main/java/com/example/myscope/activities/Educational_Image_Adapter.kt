package com.example.myscope.activities

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.myscope.R

class Educational_Image_Adapter : BaseAdapter() {

    private val mContext: Context? = null
    // Keep all Images in array
    @JvmField
    var mThumbIds = arrayOf(
            R.drawable.carona_image
    )

    override fun getCount(): Int {
        return mThumbIds.size
    }

    override fun getItem(position: Int): Any {
        return mThumbIds[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val imageView = ImageView(mContext)
        imageView.setImageResource(mThumbIds[position])
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.layoutParams = AbsListView.LayoutParams(350, 350
        )
        return imageView
    }

}