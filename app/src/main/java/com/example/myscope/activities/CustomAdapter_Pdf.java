package com.example.myscope.activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myscope.R;

import java.util.ArrayList;

public class CustomAdapter_Pdf extends RecyclerView.Adapter<CustomAdapter_Pdf.MyViewHolder> {
    //    ArrayList personNames;
    ArrayList covidImages;
    Context context;

    public CustomAdapter_Pdf(Context context, ArrayList covidImages) {
        this.context = context;
//        this.personNames = personNames;
        this.covidImages = covidImages;
    }

    @Override
    public CustomAdapter_Pdf.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout_pdf, parent, false);
        // set the view's size, margins, paddings and layout parameters
        CustomAdapter_Pdf.MyViewHolder vh = new CustomAdapter_Pdf.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomAdapter_Pdf.MyViewHolder holder, final int position) {
        // set the data in items
//        holder.name.setText(personNames.get(position).toString());
        holder.image.setImageResource(covidImages.get(position).hashCode());
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                Intent intent = new Intent(context, Full_Image_Activity.class);
                intent.putExtra("image", covidImages.get(position).hashCode()); // put image data in Intent
                context.startActivity(intent); // start Intent
            }
        });
    }

    @Override
    public int getItemCount() {
        return covidImages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
