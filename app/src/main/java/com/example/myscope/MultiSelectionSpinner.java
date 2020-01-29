package com.example.myscope;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

@SuppressLint("AppCompatCustomView")
public class MultiSelectionSpinner extends Spinner implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener
    {
        private List<String> listitems;
        private boolean[] checked;
    public MultiSelectionSpinner(Context context) {
        super(context);
    }
    public MultiSelectionSpinner(Context context, AttributeSet Attrs){
        super(context,Attrs);
    }
    public MultiSelectionSpinner(Context context, AttributeSet Attrs, int arg1){
        super(context,Attrs,arg1);
    }
        @Override
        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if(isChecked)
            checked[which]= true;
        else
            checked[which]= false;
    }
        @Override
        public void onCancel(DialogInterface dialog) {
        String str = "Selected Values are:";
        for (int i=0 ; i<listitems.size();i++){
            if(checked[i]){
                str=str+" "+listitems.get(i);
            }
        }
    }
        @Override
        public boolean performClick()
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMultiChoiceItems(listitems.toArray(new CharSequence[listitems.size()]), checked, this);
            builder.setPositiveButton("done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setOnCancelListener(this);
            builder.show();
            return true;
        }
        public void setItems(List<String> items,String allText , multispinnerlistner listener){
        this.listitems=items;
        checked = new boolean[items.size()];
        for (int i = 0; i < checked.length; i++)
            checked[i] =false;
        ArrayAdapter<String> adapter=new ArrayAdapter<String>
                (getContext(),android.R.layout.simple_spinner_item,new String[]{allText});
        setAdapter(adapter);
    }
        public interface multispinnerlistner{
            public void onItemschecked(boolean[] checked);
        }
    }
