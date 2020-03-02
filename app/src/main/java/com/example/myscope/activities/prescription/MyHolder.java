package com.example.myscope.activities.prescription;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myscope.R;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView img;
    TextView nameTxt1,posTxt;
    ItemClickListener itemClickListener;


    public MyHolder(View itemView) {
        super(itemView);

        //ASSIGN
        img= (ImageView) itemView.findViewById(R.id.playerImage);
        nameTxt1= (TextView) itemView.findViewById(R.id.nameTxt1);
        posTxt= (TextView) itemView.findViewById(R.id.posTxt);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener=ic;
    }

}
