package com.example.myscope.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myscope.R;


public class SignUp_Page extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SignUp_Page";

    Button btn_SignUp;
    TextView btn_login;
    EditText edt_firstname, edt_lastname, edt_mobile, edt_email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuppage_main);

        btn_SignUp = (Button) findViewById(R.id.btn_SignUp);
        btn_login = (TextView) findViewById(R.id.btn_login);


        edt_firstname = (EditText) findViewById(R.id.edt_firstname);
        edt_lastname = (EditText) findViewById(R.id.edt_lastname);
        edt_mobile = (EditText) findViewById(R.id.edit_mobile);
        edt_email = (EditText) findViewById(R.id.edt_email);

        btn_SignUp.setOnClickListener(this);
        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_SignUp:
                signup();

                break;
            case R.id.btn_login:

                Intent login_btn = new Intent(getApplicationContext(), Login_Page.class);
                login_btn.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(login_btn);

                break;
        }
    }

    private void signup() {
        Log.d(TAG, "SignUp_Page");

        String firstName = edt_firstname.getText().toString();
        String lastName = edt_lastname.getText().toString();
        String mobileNumber = edt_mobile.getText().toString();
        String emailId = edt_email.getText().toString();

        if (validate() == false) {

            onSignupFailed();
            return;
        }
//        sendPost(name, email, password, mobile);
        Intent login_btn = new Intent(getApplicationContext(), Login_Page.class);
        login_btn.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(login_btn);
        Toast.makeText(getApplicationContext(), "Registration Successfully Completed", Toast.LENGTH_LONG).show();
    }

    private void onSignupFailed() {

        Toast.makeText(getBaseContext(), "Please Enter all fields", Toast.LENGTH_LONG).show();

        btn_SignUp.setEnabled(true);

    }

    private boolean validate() {


        boolean valid = true;

        String firstName = edt_firstname.getText().toString();
        String lastName = edt_lastname.getText().toString();
        String mobileNumber = edt_mobile.getText().toString();
        String emailId = edt_email.getText().toString();

//        String TakenUsername = api.toString();

        if (firstName.isEmpty() || firstName.length() < 3) {
            edt_firstname.setError("at least 3 characters");
            valid = false;
        } else {
            edt_firstname.setError(null);
        }

        if (lastName.isEmpty() || lastName.length() < 3) {
            edt_lastname.setError("at least 3 characters");
            valid = false;
        } else {
            edt_lastname.setError(null);
        }


        if (mobileNumber.isEmpty() || mobileNumber.length() < 10) {
            edt_mobile.setError("enter mobile number");
            valid = false;
        } else {
            edt_mobile.setError(null);
        }

        if (emailId.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            edt_email.setError("enter a valid email address");
            valid = false;
        } else {
            edt_email.setError(null);
        }

        return valid;
    }


}


//    private void sendPost(String name, String email, String password, String mobile) {
//
//        api.registeruser(email, password, name, mobile)
//                .enqueue(new Callback<LoginResponse>() {
//                    @Override
//                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//
//                        Toast.makeText(getApplicationContext(), "Registration Success fully completed! Please Login", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(getApplicationContext(), Login_Page.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                        Log.i(TAG, "post submitted to API." + response.body().toString());
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<LoginResponse> call, Throwable t) {
//
//                        Toast.makeText(getApplicationContext(), "Please Enter Valid Login Details", Toast.LENGTH_LONG).show();
////                        Log.e(TAG, "Unable to submit post to API.");
//                    }
//                });
//
//    }
