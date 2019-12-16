package com.example.myscope.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.myscope.R;


public class Login_Page extends BaseActivity implements View.OnClickListener {

    Button btn_otp;
    TextView btn_register,btn_facebook,btn_google,change_mobile_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page_main);

        btn_otp=(Button)findViewById(R.id.btn_otp_send);
        btn_register=(TextView)findViewById(R.id.btn_register);

        btn_otp.setOnClickListener(this);
        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_otp_send:

                Intent btn_otp=new Intent(getApplicationContext(), Otp_Page.class);
                startActivity(btn_otp);

                break;

            case R.id.btn_register:

                Intent btn_register=new Intent(getApplicationContext(), SignUp_Page.class);
                startActivity(btn_register);

                break;

        }

    }
}
