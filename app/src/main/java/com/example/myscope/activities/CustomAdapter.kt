package com.soargtechnologies.myscope.activities

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soargtechnologies.myscope.R
import java.util.*

class CustomAdapter //        this.personNames = personNames;
(var context: Context, //    ArrayList personNames;
 var covidImages: ArrayList<*>) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rowlayout, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) { // set the data in items
//        holder.name.setText(personNames.get(position).toString());
        holder.image.setImageResource(covidImages[position].hashCode())
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener {
            // open another activity on item click
            val intent = Intent(context, Full_Image_Activity::class.java)
            intent.putExtra("image", covidImages[position].hashCode()) // put image data in Intent
            context.startActivity(intent) // start Intent
        }
    }

    override fun getItemCount(): Int {
        return covidImages.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // init the item view's
        var name: TextView
        var image: ImageView

        init {
            // get the reference of item view's
            name = itemView.findViewById<View>(R.id.name) as TextView
            image = itemView.findViewById<View>(R.id.image) as ImageView
        }
    }

}