package com.example.myscope.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myscope.R;


public class Otp_Page extends BaseActivity implements View.OnClickListener {

    Button btn_verify_otp;
    EditText edtOtp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otppage_main);

        btn_verify_otp=(Button)findViewById(R.id.btn_verify_otp);
        edtOtp=(EditText)findViewById(R.id.edt_otp);


        btn_verify_otp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_verify_otp:

                if (validate() == false) {

                    onSignupFailed();
                    return;
                }
                Intent btn_otp=new Intent(getApplicationContext(), Main_Activity.class);
                startActivity(btn_otp);
                Toast.makeText(getApplicationContext(), "Login Successfully Completed", Toast.LENGTH_SHORT).show();

                break;

        }

    }

    private void onSignupFailed() {

        Toast.makeText(getBaseContext(), "Please Enter valid OTP", Toast.LENGTH_LONG).show();

        btn_verify_otp.setEnabled(true);

    }
    private boolean validate() {
        boolean valid = true;

        String otpNumber = edtOtp.getText().toString();

        if (otpNumber.isEmpty() || otpNumber.length() < 6) {
            edtOtp.setError("enter OTP number");
            valid = false;
        } else {
            edtOtp.setError(null);
        }

        return valid;
    }
}
