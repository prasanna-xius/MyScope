package com.example.myscope.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myscope.R;


public class Login_Page extends BaseActivity implements View.OnClickListener {

    Button btn_otp;
    TextView btn_register, btn_facebook, btn_google, change_mobile_btn;

    EditText edtMobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page_main);

        btn_otp = (Button) findViewById(R.id.btn_otp_send);
        btn_register = (TextView) findViewById(R.id.btn_register);
        edtMobile = (EditText) findViewById(R.id.mobile_number);
        edtMobile.setFilters(new InputFilter[]{ new MinMaxFilter("1", "10")});

        change_mobile_btn = (TextView) findViewById(R.id.change_mobile_btn);

        btn_otp.setOnClickListener(this);
        change_mobile_btn.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        hideKeyBoard();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_otp_send:

                if (validate() == false) {

                    onSignupFailed();
                    return;
                }


                Intent btn_otp = new Intent(getApplicationContext(), Otp_Page.class);
                btn_otp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(btn_otp);

                break;

            case R.id.btn_register:

                Intent btn_register = new Intent(getApplicationContext(), SignUp_Page.class);
                btn_register.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(btn_register);

                break;

            case R.id.change_mobile_btn:

                Intent change_mobile_btn = new Intent(getApplicationContext(), MobileChange_Activity.class);
                change_mobile_btn.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(change_mobile_btn);

                break;
        }

    }
    private void onSignupFailed() {

        Toast.makeText(getBaseContext(), "Please Enter all fields", Toast.LENGTH_LONG).show();

        btn_otp.setEnabled(true);

    }
    private boolean validate() {
        boolean valid = true;

        String mobileNumber = edtMobile.getText().toString();

        if (mobileNumber.isEmpty() || mobileNumber.length() < 10) {
            edtMobile.setError("enter mobile number");
            valid = false;
        } else {
            edtMobile.setError(null);
        }

        return valid;
    }
}
