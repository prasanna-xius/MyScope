package com.example.myscope.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myscope.ImageUpload;
import com.example.myscope.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Prescription_manual extends AppCompatActivity {

    EditText doctorName,hospitalName;
    Calendar myCalendar;
    TextView startDate,stopDate;
    Button next;
    Drawable dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_manual);
        dr = getResources().getDrawable(R.drawable.error);
        dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());

        startDate = (TextView) findViewById(R.id.start_date);
        stopDate = (TextView) findViewById(R.id.stop_date);
        doctorName = (EditText)findViewById(R.id.doctor_name);
        hospitalName = (EditText)findViewById(R.id.hosp_name);


        next = (Button)findViewById(R.id.prescription_save);
        next.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Prescription_manual.this, ImageUpload.class);
               startActivity(intent);
//               if(doctorName.getText().toString().isEmpty())
//               {
//                   doctorName.setCompoundDrawables(null,null,dr,null);
//               }
//               else if(hospitalName.getText().toString().isEmpty())
//               {
//                   hospitalName.setCompoundDrawables(null,null,dr,null);
//               }
           }
       });

         myCalendar = Calendar.getInstance();
       final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Prescription_manual.this,R.style.MyDatePicker, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        startDate.setText(sdf.format(myCalendar.getTime()));
    }
}
