package com.example.myscope.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.myscope.R;


public class Otp_Page extends BaseActivity implements View.OnClickListener {

    Button btn_verify_otp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otppage_main);

        btn_verify_otp=(Button)findViewById(R.id.btn_verify_otp);


        btn_verify_otp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_verify_otp:

                Intent btn_otp=new Intent(getApplicationContext(), Main_Activity.class);

                startActivity(btn_otp);


                break;

        }

    }


}
